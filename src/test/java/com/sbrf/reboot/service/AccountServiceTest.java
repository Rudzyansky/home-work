package com.sbrf.reboot.service;

import com.sbrf.reboot.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked", "rawtypes"})
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountService(accountRepository);
    }

    @Test
    void contractExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(111L);

        long clientId = 1L;
        long contractNumber = 111L;

        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertTrue(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void contractNotExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(222L);

        long clientId = 1L;
        long contractNumber = 111L;

        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertFalse(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void cardExist() {
        Set<Long> cards = new HashSet();
        cards.add(4000_0000_0000_0000L);

        long contractNumber = 111L;
        long card = 4000_0000_0000_0000L;

        when(accountRepository.getAllCardsByAccount(contractNumber)).thenReturn(cards);

        assertTrue(accountService.isAccountHasCard(contractNumber, card));
    }

    @Test
    void cardNotExist() {
        Set<Long> cards = new HashSet();
        cards.add(4000_0033_1230_0000L);

        long contractNumber = 111L;
        long card = 4000_0000_0000_0000L;

        when(accountRepository.getAllCardsByAccount(contractNumber)).thenReturn(cards);

        assertFalse(accountService.isAccountHasCard(contractNumber, card));
    }

    @Test
    void repositoryHasTreeMethods() {
        assertEquals(2, AccountRepository.class.getMethods().length);
    }

    @Test
    void serviceHasTreeMethods() {
        assertEquals(2, AccountService.class.getMethods().length - Object.class.getMethods().length);
    }
}
