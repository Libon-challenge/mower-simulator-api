package com.libon.mower.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.libon.mower.exception.InvalidInstructionException;
import com.libon.mower.exception.InvalidOrientationException;
import com.libon.mower.exception.InvalidPositionException;
import com.libon.mower.model.MowerPayloadDTO;
import com.libon.mower.model.MowerRequestDTO;
import com.libon.mower.model.MowerResponseDTO;
import com.libon.mower.model.PositionDTO;

@Service
public class MowerServiceImpl implements MowerService {

    @Override
    public List<MowerResponseDTO> simulate(MowerPayloadDTO payload) {
        validatePayload(payload);

        List<MowerResponseDTO> results = new ArrayList<>();
        int maxX = payload.getField().getMaxX();
        int maxY = payload.getField().getMaxY();

        for (MowerRequestDTO mower : payload.getMowers()) {
            MowerResponseDTO response = simulateSingleMower(mower, maxX, maxY);
            results.add(response);
        }

        return results;
    }

    private MowerResponseDTO simulateSingleMower(MowerRequestDTO mower, int maxX, int maxY) {
        validateMower(mower, maxX, maxY);

        int x = mower.getStartPosition().getX();
        int y = mower.getStartPosition().getY();
        String orientation = mower.getOrientation().trim().toUpperCase();

        if (mower.getInstructions() == null || mower.getInstructions().isEmpty()) {
            throw new InvalidInstructionException("Instructions cannot be null or empty");
        }

        for (String instrRaw : mower.getInstructions()) {
            String instruction = instrRaw.trim().toUpperCase();
            validateInstruction(instruction);

            switch (instruction) {
                case "G" -> orientation = turnLeft(orientation);
                case "D" -> orientation = turnRight(orientation);
                case "A" -> {
                    PositionDTO newPos = moveForward(x, y, orientation, maxX, maxY);
                    x = newPos.getX();
                    y = newPos.getY();
                }
            }
        }

        return createResponse(mower.getId(), x, y, orientation);
    }


    private void validatePayload(MowerPayloadDTO payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null");
        }
        if (payload.getField() == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        if (payload.getMowers() == null || payload.getMowers().isEmpty()) {
            throw new IllegalArgumentException("Mowers list cannot be null or empty");
        }
        if (payload.getField().getMaxX() < 0 || payload.getField().getMaxY() < 0) {
            throw new InvalidPositionException("Field dimensions must be non-negative");
        }
    }

    private void validateMower(MowerRequestDTO mower, int maxX, int maxY) {
        if (mower.getStartPosition() == null) {
            throw new InvalidPositionException("Start position cannot be null");
        }

        int x = mower.getStartPosition().getX();
        int y = mower.getStartPosition().getY();

        if (x < 0 || x > maxX || y < 0 || y > maxY) {
            throw new InvalidPositionException(
                    "Invalid start position (" + x + ", " + y + "). Field boundaries are (0,0) to (" + maxX + ", " + maxY + ")."
            );
        }

        String orientation = mower.getOrientation();
        if (orientation == null) {
            throw new InvalidOrientationException("Orientation cannot be null");
        }

        orientation = orientation.trim().toUpperCase();
        if (!orientation.equals("N") && !orientation.equals("E") &&
                !orientation.equals("S") && !orientation.equals("W")) {
            throw new InvalidOrientationException(
                    "Invalid orientation: '" + orientation +
                            "'. Valid values are: N (North), E (East), S (South), W (West)."
            );
        }
    }

    private void validateInstruction(String instruction) {
        if (!instruction.equals("G") && !instruction.equals("D") && !instruction.equals("A")) {
            throw new InvalidInstructionException(
                    "Invalid instruction: '" + instruction +
                            "'. Valid values are: G (turn left), D (turn right), A (move forward)."
            );
        }
    }


    private String turnLeft(String orientation) {
        return switch (orientation) {
            case "N" -> "W";
            case "W" -> "S";
            case "S" -> "E";
            case "E" -> "N";
            default -> throw new InvalidOrientationException("Unexpected orientation: " + orientation);
        };
    }

    private String turnRight(String orientation) {
        return switch (orientation) {
            case "N" -> "E";
            case "E" -> "S";
            case "S" -> "W";
            case "W" -> "N";
            default -> throw new InvalidOrientationException("Unexpected orientation: " + orientation);
        };
    }

    private PositionDTO moveForward(int x, int y, String orientation, int maxX, int maxY) {
        int newX = x;
        int newY = y;

        switch (orientation) {
            case "N" -> newY = (y + 1 <= maxY) ? y + 1 : y;
            case "E" -> newX = (x + 1 <= maxX) ? x + 1 : x;
            case "S" -> newY = (y - 1 >= 0) ? y - 1 : y;
            case "W" -> newX = (x - 1 >= 0) ? x - 1 : x;
            default -> throw new InvalidOrientationException("Unexpected orientation: " + orientation);
        }

        return new PositionDTO(newX, newY);
    }


    private MowerResponseDTO createResponse(String id, int x, int y, String orientation) {
        PositionDTO finalPos = new PositionDTO(x, y);

        MowerResponseDTO response = new MowerResponseDTO();
        response.setId(id);
        response.setPosition(finalPos);
        response.setOrientation(orientation);

        return response;
    }
}
