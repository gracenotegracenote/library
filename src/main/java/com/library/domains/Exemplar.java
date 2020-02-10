package com.library.domains;

import com.library.domains.enums.ExemplarStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exemplar {
    private Integer id;
    private Integer userId;
    private ExemplarStatus status;
    private String damages;

    public Exemplar(Integer id, ExemplarStatus status) {
        this.id = id;
        this.status = status;
        this.userId = null;
        this.damages = "";
    }

    public Exemplar(Integer id, Integer userId) {
        this.id = id;
        this.status = ExemplarStatus.BORROWED;
        this.userId = userId;
        this.damages = "";
    }
}
