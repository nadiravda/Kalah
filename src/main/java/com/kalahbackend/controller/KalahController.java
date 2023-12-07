package com.kalahbackend.controller;

import com.kalahbackend.exception.IllegalGameStateException;
import com.kalahbackend.exception.InvalidMoveException;
import com.kalahbackend.model.dto.GameStateDTO;
import com.kalahbackend.model.dto.MoveDTO;
import com.kalahbackend.service.KalahService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class KalahController {

    private final KalahService kalahService;

    @PostMapping("/move")
    public ResponseEntity<String> makeMove(@RequestBody MoveDTO moveDTO) {
        try {
            kalahService.makeMove(moveDTO);
            return ResponseEntity.ok().build();
        } catch (InvalidMoveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalGameStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/game-state")
    public ResponseEntity<GameStateDTO> getGameState() {
        GameStateDTO gameState = kalahService.getGameState();
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/new-game")
    public ResponseEntity<Void> startNewGame() {
        kalahService.startNewGame();
        return ResponseEntity.ok().build();
    }
}