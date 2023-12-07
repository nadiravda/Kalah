package com.kalahbackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalahbackend.model.GameResult;
import com.kalahbackend.model.dto.GameStateDTO;
import com.kalahbackend.model.dto.MoveDTO;
import com.kalahbackend.service.KalahService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class KalahControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    KalahService kalahService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void Reinitialize() {
        kalahService.getClassicKalahGameComponent().initializeGame();
    }

    @Test
    void testMoveSuccess() throws Exception {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setFieldIndex(0);
        List<Integer> expectedBoard = List.of(0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4, 0);

        mockMvc.perform(post("/api/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveDTO)))
                .andExpect(status().isOk());

        String gameStateResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/game-state"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GameStateDTO result = objectMapper.readValue(gameStateResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(expectedBoard, result.getBoard());
        Assertions.assertEquals(2, result.getCurrentPlayer());
        Assertions.assertEquals(GameResult.GAME_IN_PROGRESS, result.getGameResult());
    }

    @Test
    void playerOneMoveAgainSuccess() throws Exception {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setFieldIndex(2);
        List<Integer> expectedBoard = List.of(4, 4, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 0);

        mockMvc.perform(post("/api/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveDTO)))
                .andExpect(status().isOk());

        String gameStateResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/game-state"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GameStateDTO result = objectMapper.readValue(gameStateResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(expectedBoard, result.getBoard());
        Assertions.assertEquals(1, result.getCurrentPlayer());
        Assertions.assertEquals(GameResult.GAME_IN_PROGRESS, result.getGameResult());
    }

    @Test
    void testMakeMoveInvalidMoveException() throws Exception {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setFieldIndex(50);

        mockMvc.perform(post("/api/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveDTO)))
                .andExpect(status().isBadRequest());
    }
}