package com.library.dtos;

import com.library.domains.CD;
import com.library.domains.Game;
import com.library.domains.Medium;
import com.library.domains.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private List<MediumDto> borrowedMedia;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = getLastName();
        this.address = user.getAddress();
        this.borrowedMedia = getMediumDtos(user.getBorrowedMedia());
    }

    /**
     * Converts provided users to DTO's.
     *
     * @param users users to convert
     * @return list of user DTO's
     */
    public static List<UserDto> convert(List<User> users) {
        List<UserDto> dtos = new ArrayList<>(users.size());
        for (User user : users) {
            dtos.add(new UserDto(user));
        }

        return dtos;
    }

    /**
     * Converts provided media to DTO's.
     *
     * @param media media to convert
     * @return list of medium DTO's
     */
    private List<MediumDto> getMediumDtos(List<Medium> media) {
        if (CollectionUtils.isEmpty(media)) {
            return Collections.emptyList();
        }

        List<MediumDto> mediaDto = new ArrayList<>(media.size());
        for (Medium medium : media) {
            if (medium instanceof Game) {
                mediaDto.add(new GameDto(medium, id, ((Game) medium).getComponents()));
            } else if (medium instanceof CD) {
                mediaDto.add(new CDDto(medium, id, ((CD) medium).getCdNumber()));
            } else {
                mediaDto.add(new MediumDto(medium, id));
            }
        }

        return mediaDto;
    }
}
