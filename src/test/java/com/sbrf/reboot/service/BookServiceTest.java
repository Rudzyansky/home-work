package com.sbrf.reboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);

        bookService = new BookService(bookRepository);
    }

    @Test
    void bookExist() {
        Book book = new Book("Goodnight Moon", "Margaret Wise Brown");
        Set<Book> books = Set.of(book);

        when(bookRepository.getAllBook()).thenReturn(books);

        Assertions.assertTrue(bookService.isBookExist(book));
    }

    @Test
    void bookNotExist() {
        Set<Book> books = Set.of(new Book("Goodnight Moon", "Margaret Wise Brown"));

        when(bookRepository.getAllBook()).thenReturn(books);

        Assertions.assertFalse(bookService.isBookExist(new Book("The Very Hungry Caterpillar", "Eric Carle")));
    }
}