package com.library.domains;

import com.library.domains.enums.ExemplarStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private Integer id;

    private String firstName;
    private String lastName;
    private String address;
    private List<Medium> borrowedMedia;

    public User(Integer id, String firstName, String lastName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.borrowedMedia = new ArrayList<>(0);
    }

    /**
     * Borrows provided medium exemplar to the user.
     *
     * @param medium     medium to borrow
     * @param exemplarId exemplar ID
     */
    public void borrow(Medium medium, Integer exemplarId) {
        Exemplar exemplar = medium.getExemplars().get(exemplarId);
        exemplar.setStatus(ExemplarStatus.BORROWED);
        exemplar.setUserId(id);
        borrowedMedia.add(medium);
    }

    /**
     * Removes provided medium exemplar from user's medium list.
     *
     * @param medium     medium
     * @param exemplarId exemplar ID
     */
    public void giveBack(Medium medium, Integer exemplarId) {
        Exemplar exemplar = medium.getExemplars().get(exemplarId);
        exemplar.setStatus(ExemplarStatus.AVAILABLE);
        exemplar.setUserId(null);
        borrowedMedia.remove(medium);
    }
}
