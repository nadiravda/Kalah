package com.kalahbackend.service;

import com.kalahbackend.exception.IllegalGameStateException;
import com.kalahbackend.exception.InvalidMoveException;
import com.kalahbackend.kalahGameEngine.ClassicKalahGameComponent;
import com.kalahbackend.model.dto.GameStateDTO;
import com.kalahbackend.model.dto.MoveDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@Service
public class KalahService {

    private final ClassicKalahGameComponent classicKalahGameComponent;

    public void makeMove(MoveDTO moveDTO) {
        if (classicKalahGameComponent.getBoard() == null) {
            classicKalahGameComponent.initializeGame();
        }

        try {
            classicKalahGameComponent.makeMove(moveDTO.getFieldIndex());
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidMoveException("Invalid move. Please provide a valid field index.");
        } catch (IllegalStateException e) {
            throw new IllegalGameStateException("Illegal game state. The game is already finished.");
        }
    }

    public GameStateDTO getGameState() {
        GameStateDTO gameState = new GameStateDTO();
        gameState.setBoard(classicKalahGameComponent.getBoard());
        gameState.setCurrentPlayer(classicKalahGameComponent.getCurrentPlayer());
        gameState.setGameResult(classicKalahGameComponent.getGameResult());
        return gameState;
    }

    public void startNewGame() {
        classicKalahGameComponent.initializeGame();
    }
}