package com.library.dtos;

import com.library.domains.enums.ExemplarStatus;
import com.library.domains.Exemplar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExemplarDto {
    private Integer id;
    private ExemplarStatus status;
    private String damages;

    public ExemplarDto(Exemplar exemplar) {
        this.id = exemplar.getId();
        this.status = exemplar.getStatus();
        this.damages = exemplar.getDamages();
    }
}
