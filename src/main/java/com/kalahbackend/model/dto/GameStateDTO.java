package com.kalahbackend.model.dto;

import com.kalahbackend.model.GameResult;
import lombok.Data;
import java.util.List;

@Data
public class GameStateDTO {
    private List<Integer> board;
    private int currentPlayer;
    private GameResult gameResult;
}