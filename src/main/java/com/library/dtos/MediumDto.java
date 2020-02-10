package com.library.dtos;

import com.library.domains.Medium;
import com.library.domains.Exemplar;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MediumDto {
    private Integer id;
    private String name;
    private String author;
    private Map<Integer, ExemplarDto> exemplars;

    public MediumDto(Medium medium, Integer userId) {
        this.id = medium.getId();
        this.name = medium.getName();
        this.author = medium.getAuthor();
        this.exemplars = getExemplarDtosForUser(medium.getExemplars(), userId);
    }

    /**
     * Converts provided user's medium exemplars to DTO's.
     *
     * @param exemplars medium exemplars to convert
     * @param userId    user ID
     * @return list of medium exemplars which are borrowed by provided user
     */
    private static Map<Integer, ExemplarDto> getExemplarDtosForUser(Map<Integer, Exemplar> exemplars, Integer userId) {
        if (CollectionUtils.isEmpty(exemplars)) {
            return Collections.emptyMap();
        }

        Map<Integer, ExemplarDto> exemplarDtos = new HashMap<>(exemplars.size());
        for (Map.Entry<Integer, Exemplar> entry : exemplars.entrySet()) {
            Exemplar exemplar = entry.getValue();
            if (userId.equals(exemplar.getUserId())) {
                exemplarDtos.put(entry.getKey(), new ExemplarDto(entry.getValue()));
            }
        }

        return exemplarDtos;
    }
}
