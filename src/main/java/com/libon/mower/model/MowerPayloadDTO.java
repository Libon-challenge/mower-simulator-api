package com.libon.mower.model;

import java.util.List;

public class MowerPayloadDTO {
    private FieldDTO field;
    private List<MowerRequestDTO> mowers;

    public FieldDTO getField() {
        return field;
    }
    public void setField(FieldDTO field) {
        this.field = field;
    }
    public List<MowerRequestDTO> getMowers() {
        return mowers;
    }
    public void setMowers(List<MowerRequestDTO> mowers) {
        this.mowers = mowers;
    }
}
