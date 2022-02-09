package com.sbrf.reboot.streams;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTest {

    /*
     * Отсортируйте коллекцию integers по возрастанию. Используйте Stream.
     */
    @Test
    public void sortedListStream() {
        List<Integer> integers = Arrays.asList(6, 9, 8, 3);

        List<Integer> expectedIntegers = Arrays.asList(3, 6, 8, 9);

        List<Integer> actualIntegers = integers.stream().sorted().collect(Collectors.toList());

        assertEquals(expectedIntegers, actualIntegers);
    }

    /*
     * Отфильтруйте коллекцию integers.
     * В коллекции должны остаться только те числа, которые делятся без остатка на 2.  Используйте Stream.
     */
    @Test
    public void filteredListStream() {
        List<Integer> integers = Arrays.asList(6, 9, 8, 3);

        List<Integer> expectedIntegers = Arrays.asList(6, 8);

        List<Integer> actualIntegers = integers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());

        assertEquals(expectedIntegers, actualIntegers);

    }

    /*
     * Отфильтруйте и отсортируйте коллекцию books.
     * Получите коллекцию, в которой будут только книги от автора "Maria", отсортированные по цене.
     * Используйте Stream.
     */
    @SuppressWarnings("InnerClassMayBeStatic")
    @AllArgsConstructor
    @EqualsAndHashCode
    class Book {
        private String name;
        private String author;
        private BigDecimal price;
    }

    @Test
    public void sortedAndFilteredBooks() {
        List<Book> books = Arrays.asList(
                new Book("Trees", "Maria", new BigDecimal(900)),
                new Book("Animals", "Tom", new BigDecimal(500)),
                new Book("Cars", "John", new BigDecimal(200)),
                new Book("Birds", "Maria", new BigDecimal(100)),
                new Book("Flowers", "Tom", new BigDecimal(700))

        );
        List<Book> expectedBooks = Arrays.asList(
                new Book("Birds", "Maria", new BigDecimal(100)),
                new Book("Trees", "Maria", new BigDecimal(900))

        );

        List<Book> actualBooks = books.stream()
                .filter(b -> "Maria".equals(b.author))
                .sorted(Comparator.comparing(o -> o.price))
                .collect(Collectors.toList());

        assertEquals(expectedBooks, actualBooks);

    }

    /*
     * Получите измененную коллекцию contracts.
     * Получите коллекцию, в которой будет тот же набор строк, только у каждой строки появится префикс "M-".
     * Используйте Stream.
     */
    @Test
    public void modifiedList() {
        List<String> contracts = Arrays.asList("NCC-1-CH", "NCC-2-US", "NCC-3-NH");

        List<String> expectedContracts = Arrays.asList("M-NCC-1-CH", "M-NCC-2-US", "M-NCC-3-NH");

        List<String> actualContracts = contracts.stream().map(s -> "M-" + s).collect(Collectors.toList());

        assertEquals(expectedContracts, actualContracts);

    }

    @Test
    public void limitTest() {
        long count = 15;
        Random rnd = new Random();

        List<Integer> randoms = IntStream.generate(rnd::nextInt)
                .limit(count)
                .boxed()
                .collect(Collectors.toList());

        assertEquals(count, randoms.size());
    }

    @Test
    public void maxLineTest() {
        String[] strings = {
                "Hello",
                "Word",
                "AZAZAZAZA",
                "Nya kawaii desu sempai^^",
                "ЫВа",
                "Sasai Kudasai"
        };

        int maxLineSize = Arrays.stream(strings).mapToInt(String::length).max().getAsInt();

        assertEquals(24, maxLineSize);
    }

    @Test
    public void flatMapUsageTest() {
        List<User> users = Arrays.asList(
                new User("Dominator2003", Permissions.USER, Permissions.PATAY),
                new User("LeshaNavalivaetYT", Permissions.USER),
                new User("CECYPUTU", Permissions.MODERATOR),
                new User("♂GA4I♂", Permissions.USER),
                new User("ToDeathSun", Permissions.USER),
                new User("♂DUNGEON MASTER♂", Permissions.ADMIN),
                new User("♂FIVE FINGERS FIST FEST♂", Permissions.MODERATOR, Permissions.PATAY),
                new User("UniverseChan", Permissions.USER),
                new User("ne spam bot atvi4ay", Permissions.USER, Permissions.PATAY),
                new User("NekoChan", Permissions.MODERATOR),
                new User("CoChan", Permissions.USER),
                new User("KOChan", Permissions.ADMIN),
                new User("CAPTCHAtsky", Permissions.USER),
                new User("StringOFF", Permissions.USER),
                new User("Непреднамеренный панчлайн", Permissions.USER),
                new User("Сын маминой подруги", Permissions.PATAY)
        );
        List<String> onlineList = Arrays.asList(
                "ne spam bot atvi4ay",
                "♂DUNGEON MASTER♂", // admin
                "CoChan",
                "CECYPUTU", // moder
                "NekoChan", //moder
                "LeshaNavalivaetYT",
                "CAPTCHAtsky",
                "Сын маминой подруги"
        );
        users.stream()
                .filter(u -> onlineList.stream().anyMatch(n -> n.equals(u.name)))
                .forEach(u -> u.setOnline(true));

        long staffOnlineCount = users.stream()
                .filter(User::isOnline)
                .map(User::getPermissions)
                .flatMap(Collection::stream)
                .filter(Permissions::isStaff)
                .count();

        assertEquals(3, staffOnlineCount);
    }

    enum Permissions {
        GUEST,
        PATAY,
        USER,
        MODERATOR,
        ADMIN;

        public static boolean isStaff(Permissions permissions) {
            return permissions == MODERATOR || permissions == ADMIN;
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    @Getter
    class User {
        private final String name;
        private final Set<Permissions> permissions;
        @Setter
        private boolean online;

        public User(String name, Permissions... permissions) {
            this.name = name;
            this.permissions = Arrays.stream(permissions).collect(Collectors.toSet());
        }
    }
}
