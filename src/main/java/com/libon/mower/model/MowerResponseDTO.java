package com.libon.mower.model;

public class MowerResponseDTO {
    private String id;
    private PositionDTO position;
    private String orientation;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public PositionDTO getPosition() {
        return position;
    }
    public void setPosition(PositionDTO position) {
        this.position = position;
    }
    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
