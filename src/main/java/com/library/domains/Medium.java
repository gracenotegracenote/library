package com.library.domains;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Medium {
    private Integer id;
    private String name;
    private String author;
    private Map<Integer, Exemplar> exemplars;

    public Medium(Integer id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.exemplars = new HashMap<>(0);
    }

    /**
     * Adds provided exemplar to the current medium.
     *
     * @param exemplar exemplar to add
     */
    public void addExemplar(Exemplar exemplar) {
        if (exemplar == null) {
            return;
        }

        exemplars.put(exemplar.getId(), exemplar);
    }
}
