package com.yuzuhard.dto;

import lombok.Data;

@Data
public class CategoryDto {

    int id;
    String name;
    String description;
    int count;
    String status;

    public CategoryDto() {
    }

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
