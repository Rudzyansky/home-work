package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerH2RepositoryTest {

    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void before() {
        customerRepository = new CustomerH2Repository();
        ((CustomerH2Repository) customerRepository).dropTable();
        ((CustomerH2Repository) customerRepository).createTableIfNotExists();
    }

    @Test
    void getAll() {
        boolean tomCreated = customerRepository.createCustomer("Tom", "tom@ya.ru");

        List<Customer> all = customerRepository.getAll();

        assertTrue(all.size() != 0);
    }

    @Test
    void createCustomer() {
        boolean mariaCreated = customerRepository.createCustomer("Maria", "maria98@ya.ru");

        assertTrue(mariaCreated);
    }

    @Test
    void customerExists() {
        boolean aliceCreated = customerRepository.createCustomer("Alice", "alice@ya.ru");

        boolean isAliceExists = customerRepository.isCustomerExists("Alice");

        assertTrue(isAliceExists);
    }

    @Test
    void customerDoesNotExists() {
        boolean isEveExists = customerRepository.isCustomerExists("Eve");

        assertFalse(isEveExists);
    }

    @Test
    void updateEMail() {
        String username = "Bob";
        String email = "bob@ya.ru";

        String newEmail = "eve@ya.ru";

        customerRepository.createCustomer(username, email);

        boolean updated = customerRepository.updateCustomerEMailByUserName(username, newEmail);

        //noinspection OptionalGetWithoutIsPresent
        String actual = customerRepository.getAll().stream()
                .filter(c -> username.equals(c.getName()))
                .map(Customer::getEMail)
                .findFirst()
                .get();

        assertEquals(newEmail, actual);
        assertTrue(updated);
    }
}
