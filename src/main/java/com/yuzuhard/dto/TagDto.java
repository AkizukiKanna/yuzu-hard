package com.yuzuhard.dto;

import lombok.Data;

@Data
public class TagDto {

    int id;
    String name;
    String description;
    int count;
    String status;

    public TagDto() {
    }

    public TagDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
