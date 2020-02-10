package com.library;

import com.library.domains.*;
import com.library.domains.enums.ExemplarStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public static List<User> users = initUsers();
    public static List<Medium> media = initMedia();

    public static Map<Integer, User> userMap = initUserMap();
    public static Map<Integer, Medium> mediaMap = initMediaMap();

    private static List<User> initUsers() {
        User user1 = new User(1, "Jan", "", "Nockherstraße 4, 81541 München");
        User user2 = new User(2, "Hannes", "", "Nockherstraße 4, 81541 München");
        User user3 = new User(3, "Johannes", "", "Nockherstraße 4, 81541 München");
        User user4 = new User(4, "Mila", "", "Connollystraße 3, 80809 München");

        return Arrays.asList(user1, user2, user3, user4);
    }

    private static List<Medium> initMedia() {
        Medium medium1 = new Book(1, "Java Lehrbuch", "IT Professor");
        initExemplars(medium1, 2);

        Medium medium2 = new Book(2, "Compliance from Scratch", "Jura Professor");
        initExemplars(medium2, 1);

        Medium medium3 = new Book(3, "Faust", "Goethe");
        initExemplars(medium3, 1);

        Medium medium4 = new Magazin(4, "Javascript heute", "Burda");
        initExemplars(medium4, 3);

        Medium medium5 = new CD(5, "The Wall", "Pink Floyd", 2);
        initExemplars(medium5, 1);

        Medium medium6 = new CD(6, "Age of Empires III", "Microsoft", 1);
        initExemplars(medium6, 1);

        Medium medium7 = new Game(7, "Monopoly", "Brettspiele Deutschlands", "Eine Karte, 4 Spielfiguren");
        initExemplars(medium7, 1);

        return Arrays.asList(medium1, medium2, medium3, medium4, medium5, medium6, medium7);
    }

    private static Map<Integer, User> initUserMap() {
        Map<Integer, User> userMap = new HashMap<>(users.size());

        for (User user : users) {
            userMap.put(user.getId(), user);
        }

        return userMap;
    }

    private static Map<Integer, Medium> initMediaMap() {
        Map<Integer, Medium> mediaMap = new HashMap<>(media.size());

        for (Medium medium : media) {
            mediaMap.put(medium.getId(), medium);
        }

        return mediaMap;
    }

    private static void initExemplars(Medium medium, int number) {
        if (number < 1) {
            return;
        }

        for (int i = 1; i <= number; i++) {
            medium.addExemplar(new Exemplar(i, ExemplarStatus.AVAILABLE));
        }
    }
}
