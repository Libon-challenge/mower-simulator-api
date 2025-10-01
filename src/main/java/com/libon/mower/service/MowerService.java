package com.libon.mower.service;

import java.util.List;

import com.libon.mower.model.MowerPayloadDTO;
import com.libon.mower.model.MowerResponseDTO;

public interface MowerService {
    List<MowerResponseDTO> simulate(MowerPayloadDTO payload);
}
