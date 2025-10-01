package com.libon.mower.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libon.mower.model.MowerPayloadDTO;
import com.libon.mower.model.MowerResponseDTO;
import com.libon.mower.service.MowerService;

@RestController
@RequestMapping("/mowers")
public class MowerController {

    private final MowerService mowerService;

    public MowerController(MowerService mowerService) {
        this.mowerService = mowerService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<List<MowerResponseDTO>> simulate(@RequestBody MowerPayloadDTO payload) {
        List<MowerResponseDTO> result = mowerService.simulate(payload);
        return ResponseEntity.ok(result);
    }
}