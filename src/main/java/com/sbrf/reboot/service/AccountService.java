package com.sbrf.reboot.service;

import com.sbrf.reboot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public boolean isClientHasContract(long clientId, long contractNumber) {
        return accountRepository.getAllAccountsByClientId(clientId).stream().anyMatch(c -> c == contractNumber);
    }

    public boolean isAccountHasCard(long contractNumber, long card) {
        return accountRepository.getAllCardsByAccount(contractNumber).stream().anyMatch(c -> c == card);
    }
}
