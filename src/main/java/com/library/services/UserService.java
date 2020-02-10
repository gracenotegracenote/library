package com.library.services;

import com.library.domains.Medium;
import com.library.domains.User;
import com.library.domains.enums.ExemplarStatus;
import com.library.Data;
import com.library.domains.Exemplar;
import com.library.domains.enums.ServiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final MediumService mediumService;

    public UserService(MediumService mediumService) {
        this.mediumService = mediumService;
    }

    public List<User> getAllUsers() {
        return Data.users;
    }

    public User getUser(Integer userId) {
        return Data.userMap.getOrDefault(userId, null);
    }

    public ServiceStatus borrow(User user, Integer mediumId, Integer exemplarId) {
        Medium medium = mediumService.getMedium(mediumId);

        ServiceStatus validation = validate(user, medium, exemplarId);
        if (!ServiceStatus.SUCCESSFUL.equals(validation)) {
            return validation;
        }

        Exemplar exemplar = medium.getExemplars().get(exemplarId);

        ExemplarStatus status = exemplar.getStatus();
        if (status.equals(ExemplarStatus.BORROWED)) {
            return ServiceStatus.ALREADY_BORROWED;
        }

        if (!status.equals(ExemplarStatus.AVAILABLE)) {
            return ServiceStatus.MEDIUM_NOT_AVAILABLE;
        }

        user.borrow(medium, exemplarId);
        return ServiceStatus.SUCCESSFUL;
    }

    public ServiceStatus giveBack(User user, Integer mediumId, Integer exemplarId) {
        Medium medium = mediumService.getMedium(mediumId);

        ServiceStatus validation = validate(user, medium, exemplarId);
        if (!ServiceStatus.SUCCESSFUL.equals(validation)) {
            return validation;
        }

        Exemplar exemplar = medium.getExemplars().get(exemplarId);

        ExemplarStatus status = exemplar.getStatus();
        if (!status.equals(ExemplarStatus.BORROWED) || !user.getBorrowedMedia().contains(medium)) {
            return ServiceStatus.MEDIUM_IS_NOT_BORROWED;
        }

        user.giveBack(medium, exemplarId);
        return ServiceStatus.SUCCESSFUL;
    }

    private ServiceStatus validate(User user, Medium medium, Integer exemplarId) {
        if (user == null) {
            return ServiceStatus.USER_NOT_FOUND;
        }

        if (medium == null) {
            return ServiceStatus.MEDIUM_NOT_FOUND;
        }

        Exemplar exemplar = medium.getExemplars().getOrDefault(exemplarId, null);
        if (exemplar == null) {
            return ServiceStatus.EXEMPLAR_NOT_FOUND;
        }

        return ServiceStatus.SUCCESSFUL;
    }

}
