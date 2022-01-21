package com.sbrf.reboot.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {
    /*
     * Задача.
     * Имеется список лучших студентов вуза.
     *
     * 1. Иванов
     * 2. Петров
     * 3. Сидоров
     *
     * В новом семестре по результатам подсчетов оценок в рейтинг на 1 место добавился новый студент - Козлов.
     * Теперь в рейтинге участвуют 4 студента.
     * (Предполагаем что в рейтинг можно попасть только получив достаточное количество балов, что бы занять 1 место).
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете для текущего хранения и использования списка студентов?
     *
     * Проинициализируйте students, добавьте в нее 4 фамилии студентов что бы тест завершился успешно.
     */
    @Test
    public void addStudentToRating() {
        List<String> students = new ArrayList<>(3);

        students.add(0, "Иванов");
        students.add(1, "Петров");
        students.add(2, "Сидоров");

        students.add(0, "Козлов");

        assertEquals(4, students.size());
    }

    /*
     * Задача.
     * Вы коллекционируете уникальные монеты.
     * У вас имеется специальный бокс с секциями, куда вы складываете монеты в хаотичном порядке.
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете использовать для хранения монет в боксе.
     *
     * Проинициализируйте moneyBox, добавьте в нее 10 монет что бы тест завершился успешно.
     */
    @Test
    public void addMoneyToBox() {
        Set<Integer> moneyBox = new HashSet<>(10);

        moneyBox.add(1);
        moneyBox.add(2);
        moneyBox.add(5);

        moneyBox.add(10);
        moneyBox.add(20);
        moneyBox.add(25);
        moneyBox.add(50);

        moneyBox.add(100);
        moneyBox.add(200);
        moneyBox.add(500);

        assertEquals(10, moneyBox.size());
    }

    /*
     * Задача.
     * Имеется книжная полка.
     * Периодически вы берете книгу для чтения, затем кладете ее на свое место.
     *
     * Вопрос.
     * Какую коллекцию из реализаций интерфейса Collection вы предпочтете использовать для хранения книг.
     *
     * Проинициализируйте bookshelf, добавьте в нее 3 книги что бы тест завершился успешно.
     */
    @Test
    public void addBookToShelf() {
        class Book {
        }

        List<Book> bookshelf = new ArrayList<>(3);

        bookshelf.add(new Book());
        bookshelf.add(new Book());
        bookshelf.add(new Book());

        int location = 1;
        Book current = bookshelf.get(location);

        assertEquals(3, bookshelf.size());
    }
}
