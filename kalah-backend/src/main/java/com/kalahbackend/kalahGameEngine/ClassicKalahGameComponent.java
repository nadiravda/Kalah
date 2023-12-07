package com.kalahbackend.kalahGameEngine;

import com.kalahbackend.exception.IllegalGameStateException;
import com.kalahbackend.exception.InvalidMoveException;
import com.kalahbackend.model.GameResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@Setter
@Component
public class ClassicKalahGameComponent {

    private static final int BOARD_SIZE = 14;
    private static final int INITIAL_STONES = 4;
    private static int playerOneHouseIndex = BOARD_SIZE / 2 - 1;
    private static int playerTwoHouseIndex = BOARD_SIZE - 1;

    private List<Integer> board;
    private int currentPlayer;
    private GameResult gameResult;

    public ClassicKalahGameComponent() {
        initializeGame();
    }
    public void initializeGame() {
        board = new ArrayList<>();
        currentPlayer = 1;
        gameResult = GameResult.GAME_IN_PROGRESS;
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.add((i == BOARD_SIZE / 2 - 1 || i == BOARD_SIZE - 1) ? 0 : INITIAL_STONES);
        }
    }
    public void makeMove(int fieldIndex) {
        if (!isGameInitialized()) {
            throw new IllegalGameStateException("Game not initialized. Please start a new game.");
        }

        if (!isValidFieldIndex(fieldIndex)) {
            throw new InvalidMoveException("Invalid move. Please provide a valid field index.");
        }

        var stones = board.get(fieldIndex);
        board.set(fieldIndex, 0);

        currentPlayer = (fieldIndex <= BOARD_SIZE / 2 - 1 ? 1 : 2);
        var currentIndex = fieldIndex;

        while (stones > 0) {
            currentIndex = (currentIndex + 1) % BOARD_SIZE;

            if (currentPlayer == 2 && currentIndex == (BOARD_SIZE / 2 - 1)) {
                continue;
            }
            if (currentPlayer == 1 && currentIndex == (BOARD_SIZE - 1)) {
                continue;
            }
            board.set(currentIndex, board.get(currentIndex) + 1);
            stones--;
        }

        if((currentIndex!=playerOneHouseIndex && currentIndex!=playerTwoHouseIndex) && board.get(BOARD_SIZE - currentIndex - 2) != 0) {
            handleCaptures(currentIndex);
        }

        if (currentIndex != (currentPlayer == 1 ? playerOneHouseIndex : playerTwoHouseIndex)) {
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }
        determineWinner();
    }
    private void handleCaptures(int currentIndex) {
        var currentPlayerHouse = (currentPlayer == 1) ? BOARD_SIZE / 2 - 1 : BOARD_SIZE - 1;

        if (currentPlayer == 1 && board.get(currentIndex) == 1 && currentIndex < BOARD_SIZE / 2 - 1 ) {
            applyCaptures(currentIndex, currentPlayerHouse);
        }

        if (currentPlayer == 2 && board.get(currentIndex) == 1 && currentIndex > BOARD_SIZE / 2  - 1 && currentIndex < BOARD_SIZE  - 1) {
            applyCaptures(currentIndex, currentPlayerHouse);
        }
    }
    private void applyCaptures(int currentIndex, int currentPlayerHouse) {
        var oppositeIndex = BOARD_SIZE - currentIndex - 2;
        var capturedStones = board.get(oppositeIndex);

        board.set(oppositeIndex, 0);
        board.set(currentIndex, 0);
        board.set(currentPlayerHouse, board.get(currentPlayerHouse) + capturedStones + 1);
    }
    private void determineWinner() {
        var playerOneStones = calculateStonesInSide(0, BOARD_SIZE / 2 - 2);
        var playerTwoStones = calculateStonesInSide(BOARD_SIZE / 2, BOARD_SIZE - 2);

        if (playerOneStones == 0) {
            board.set(playerTwoHouseIndex, board.get(playerTwoHouseIndex) + playerTwoStones);
        } else if (playerTwoStones == 0) {
            board.set(playerOneHouseIndex, board.get(playerOneHouseIndex) + playerOneStones);
        } else {
            return;
        }

        var comparisonResult = Integer.compare(board.get(playerOneHouseIndex), board.get(playerTwoHouseIndex));
        var result = (comparisonResult > 0) ? GameResult.PLAYER_ONE_WINS :
                (comparisonResult < 0) ? GameResult.PLAYER_TWO_WINS :
                        GameResult.TIE;

        clearRemainingStones();
        setGameResult(result);
    }
    private int calculateStonesInSide(int startIndex, int endIndex) {
        return IntStream.rangeClosed(startIndex, endIndex)
                .map(board::get)
                .sum();
    }
    private void clearRemainingStones() {
        IntStream.range(0, BOARD_SIZE)
                .filter(i -> i != playerOneHouseIndex && i != playerTwoHouseIndex)
                .forEach(i -> board.set(i, 0));
    }
    private boolean isGameInitialized() {
        return board != null;
    }
    private boolean isValidFieldIndex(int fieldIndex) {
        return fieldIndex >= 0 && fieldIndex < BOARD_SIZE;
    }
}