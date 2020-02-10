package com.library.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game extends Medium {
    private String components;

    public Game(Integer id, String name, String author, String components) {
        super(id, name, author);
        this.components = components;
    }
}
