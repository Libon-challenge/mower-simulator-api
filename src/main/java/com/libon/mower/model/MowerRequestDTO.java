package com.libon.mower.model;

import java.util.List;

public class MowerRequestDTO {

    private String id;
    private PositionDTO startPosition;
    private String orientation;
    private List<String> instructions;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public PositionDTO getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(PositionDTO startPosition) {
        this.startPosition = startPosition;
    }
    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    public List<String> getInstructions() {
        return instructions;
    }
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }    
}
