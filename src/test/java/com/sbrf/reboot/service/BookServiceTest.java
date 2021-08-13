package com.sbrf.reboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.AssertTrue.*;
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
        Set<Book> books = new HashSet();
        books.add(book);

        when(bookRepository.getAllBook()).thenReturn(books);

        assertTrue(bookService.isBookExist(book));
    }

    @Test
    void bookNotExist() {
        Set<Book> books = new HashSet();
        books.add(new Book("Goodnight Moon", "Margaret Wise Brown"));

        when(bookRepository.getAllBook()).thenReturn(books);

        assertFalse(bookService.isBookExist(new Book("The Very Hungry Caterpillar", "Eric Carle")));
    }
}