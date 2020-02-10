package com.library.dtos;

import com.library.domains.Medium;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDDto extends MediumDto {
    private Integer cdNumber;

    public CDDto(Medium medium, Integer userId, Integer cdNumber) {
        super(medium, userId);
        this.cdNumber = cdNumber;
    }
}
