package com.library.controllers;

import com.library.domains.User;
import com.library.domains.Medium;
import com.library.domains.enums.ServiceStatus;
import com.library.dtos.UserDto;
import com.library.services.MediumService;
import com.library.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final UserService userService;
    private final MediumService mediumService;

    public Controller(UserService userService, MediumService mediumService) {
        this.userService = userService;
        this.mediumService = mediumService;
    }

    /**
     * Returns a greeting.
     *
     * @return greeting message
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from the Library!";
    }

    /**
     * Retrieves all users.
     *
     * @return list of users
     */
    @RequestMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.getAllUsers();
        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(UserDto.convert(users), HttpStatus.OK);
    }

    /**
     * Retrieves all media.
     *
     * @return list of media
     */
    @RequestMapping("/media")
    public ResponseEntity<List<Medium>> getMedia() {
        List<Medium> media = mediumService.getAllMedia();
        if (CollectionUtils.isEmpty(media)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    /**
     * Borrows a medium exemplar to a user.
     *
     * @param userId     user ID
     * @param mediumId   medium ID
     * @param exemplarId exemplar ID
     * @return current user
     */
    @RequestMapping("/borrow")
    public ResponseEntity<Object> borrow(@RequestParam(name = "user") Integer userId,
                                         @RequestParam(name = "med") Integer mediumId,
                                         @RequestParam(name = "ex") Integer exemplarId) {

        User user = userService.getUser(userId);
        ServiceStatus status = userService.borrow(user, mediumId, exemplarId);
        if (!ServiceStatus.SUCCESSFUL.equals(status)) {
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
    }

    /**
     * Returns borrowed book to the library.
     *
     * @param userId     user ID
     * @param mediumId   medium ID
     * @param exemplarId exemplar ID
     * @return current user
     */
    @RequestMapping("/giveback")
    public ResponseEntity<Object> giveBack(@RequestParam(name = "user") Integer userId,
                                           @RequestParam(name = "med") Integer mediumId,
                                           @RequestParam(name = "ex") Integer exemplarId) {

        User user = userService.getUser(userId);
        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);
        if (!ServiceStatus.SUCCESSFUL.equals(status)) {
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
    }

}
