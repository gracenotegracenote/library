package com.library.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CD extends Medium {
    private Integer cdNumber;

    public CD(Integer id, String name, String author, Integer cdNumber) {
        super(id, name, author);
        this.cdNumber = cdNumber;
    }
}
