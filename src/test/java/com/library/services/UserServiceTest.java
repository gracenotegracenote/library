package com.library.services;

import com.library.Data;
import com.library.domains.Book;
import com.library.domains.Exemplar;
import com.library.domains.Medium;
import com.library.domains.User;
import com.library.domains.enums.ExemplarStatus;
import com.library.domains.enums.ServiceStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private MediumService mediumService;

    @Test
    void getAllUsers() {
        List<User> users = userService.getAllUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(Data.users.size(), users.size());
    }

    @Test
    void getUser() {
        int userId = 1;
        User user = userService.getUser(userId);
        Assert.assertNotNull(user);
    }

    @Test
    void borrow() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.AVAILABLE);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.borrow(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.SUCCESSFUL, status);
        Assert.assertEquals(ExemplarStatus.BORROWED, exemplar.getStatus());
        Assert.assertEquals(userId, exemplar.getUserId().intValue());
        Assert.assertEquals(1, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void borrowFail_whenAlreadyBorrowed() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.BORROWED);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.borrow(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.ALREADY_BORROWED, status);
        Assert.assertEquals(ExemplarStatus.BORROWED, exemplar.getStatus());
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void borrowFail_whenMediumNotAvailable() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.IN_PROCESSING);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.borrow(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_NOT_AVAILABLE, status);
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void borrowFail_whenMediumNotFound() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");

        Mockito.doReturn(null)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.borrow(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_NOT_FOUND, status);
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void giveBack() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.AVAILABLE);
        medium.addExemplar(exemplar);
        user.borrow(medium, exemplarId);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.SUCCESSFUL, status);
        Assert.assertEquals(ExemplarStatus.AVAILABLE, exemplar.getStatus());
        Assert.assertNull(exemplar.getUserId());
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void giveBackFail_whenNotBorrowed() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.AVAILABLE);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_IS_NOT_BORROWED, status);
        Assert.assertEquals(ExemplarStatus.AVAILABLE, exemplar.getStatus());
        Assert.assertNull(exemplar.getUserId());
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void giveBackFail_whenBorrowedFromOthers() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, 2);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_IS_NOT_BORROWED, status);
        Assert.assertEquals(ExemplarStatus.BORROWED, exemplar.getStatus());
        Assert.assertNotNull(exemplar.getUserId());
        Assert.assertNotEquals(userId, exemplar.getUserId().intValue());
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void giveBackFail_whenMediumNotAvailable() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");
        Medium medium = new Book(mediumId, "Java Lehrbuch", "IT Professor");
        Exemplar exemplar = new Exemplar(exemplarId, ExemplarStatus.IN_PROCESSING);
        medium.addExemplar(exemplar);

        Mockito.doReturn(medium)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_IS_NOT_BORROWED, status);
        Assert.assertEquals(ExemplarStatus.IN_PROCESSING, exemplar.getStatus());
        Assert.assertNull(exemplar.getUserId());
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }

    @Test
    void giveBackFail_whenMediumNotFound() {
        int userId = 1;
        int mediumId = 1;
        int exemplarId = 1;

        User user = new User(userId, "Klaus", "Meine", "Unter den Linden 100, Berlin");

        Mockito.doReturn(null)
                .when(mediumService)
                .getMedium(mediumId);

        ServiceStatus status = userService.giveBack(user, mediumId, exemplarId);

        Assert.assertEquals(ServiceStatus.MEDIUM_NOT_FOUND, status);
        Assert.assertEquals(0, user.getBorrowedMedia().size());

        Mockito.verify(mediumService, Mockito.times(1)).getMedium(mediumId);
    }
}
