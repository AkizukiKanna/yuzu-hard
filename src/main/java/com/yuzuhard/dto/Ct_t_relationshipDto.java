package com.yuzuhard.dto;

import lombok.Data;

@Data
public class Ct_t_relationshipDto {
    int tagId;
    String tagName;
    int[] tagIds;

    public Ct_t_relationshipDto() {
    }

    public Ct_t_relationshipDto(int[] tagIds) {
        this.tagIds = tagIds;
    }

    public Ct_t_relationshipDto(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
