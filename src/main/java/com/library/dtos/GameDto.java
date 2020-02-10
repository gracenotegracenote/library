package com.library.dtos;

import com.library.domains.Medium;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDto extends MediumDto {
    private String components;

    public GameDto(Medium medium, Integer userId, String components) {
        super(medium, userId);
        this.components = components;
    }
}
