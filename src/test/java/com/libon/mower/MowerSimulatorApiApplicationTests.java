package com.libon.mower;

import com.libon.mower.exception.InvalidInstructionException;
import com.libon.mower.model.*;
import com.libon.mower.service.MowerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MowerSimulatorApiApplicationTests {

    private MowerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new MowerServiceImpl();
    }

    @Test
    void testSimulateSuccess() {
        FieldDTO field = new FieldDTO();
        field.setMaxX(5);
        field.setMaxY(5);

        MowerRequestDTO mower = new MowerRequestDTO();
        mower.setId("M1");
        mower.setStartPosition(new PositionDTO(1, 2));
        mower.setOrientation("N");
        mower.setInstructions(Arrays.asList("G", "A", "G", "A", "G", "A", "G", "A", "A"));

        MowerPayloadDTO payload = new MowerPayloadDTO();
        payload.setField(field);
        payload.setMowers(List.of(mower));

        List<MowerResponseDTO> results = service.simulate(payload);

        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getPosition().getX());
        assertEquals(3, results.get(0).getPosition().getY());
        assertEquals("N", results.get(0).getOrientation());
    }

    @Test
    void testMultipleMowers() {
        FieldDTO field = new FieldDTO();
        field.setMaxX(5);
        field.setMaxY(5);

        MowerRequestDTO mower1 = new MowerRequestDTO();
        mower1.setId("M1");
        mower1.setStartPosition(new PositionDTO(1, 2));
        mower1.setOrientation("N");
        mower1.setInstructions(Arrays.asList("A", "D", "A", "D"));

        MowerRequestDTO mower2 = new MowerRequestDTO();
        mower2.setId("M2");
        mower2.setStartPosition(new PositionDTO(3, 3));
        mower2.setOrientation("E");
        mower2.setInstructions(Arrays.asList("A", "A", "G", "A"));

        MowerPayloadDTO payload = new MowerPayloadDTO();
        payload.setField(field);
        payload.setMowers(Arrays.asList(mower1, mower2));

        List<MowerResponseDTO> results = service.simulate(payload);

        assertEquals(2, results.size());
        assertEquals("M1", results.get(0).getId());
        assertEquals("M2", results.get(1).getId());
    }

    @Test
    void testInvalidInstruction() {
        FieldDTO field = new FieldDTO();
        field.setMaxX(5);
        field.setMaxY(5);

        MowerRequestDTO mower = new MowerRequestDTO();
        mower.setId("M1");
        mower.setStartPosition(new PositionDTO(1, 2));
        mower.setOrientation("N");
        mower.setInstructions(List.of("X"));

        MowerPayloadDTO payload = new MowerPayloadDTO();
        payload.setField(field);
        payload.setMowers(List.of(mower));

        assertThrows(InvalidInstructionException.class, () -> service.simulate(payload));
    }
}
