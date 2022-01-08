package com.sbrf.reboot.repository;

import com.sbrf.reboot.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileAccountRepositoryTest {
    AccountRepository accountRepository;
    AccountService accountService;

    final String FILE_NAME = "Accounts.txt";
    final byte[] CONTENT = "111:1111,1112\n33123:3311,3322,3333,3344\n444:441,442,443".getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    void setUp() throws IOException {
        accountRepository = new FileAccountRepository();
        accountService = new AccountService(accountRepository);

        Files.write(Paths.get(FILE_NAME), CONTENT);
    }

    @Test
    void replaceContractNumber() throws IOException {
        Random random = new Random();
        int index = random.nextInt((int) Files.lines(Paths.get(FILE_NAME)).count());
        String clientIdStr = Files.lines(Paths.get(FILE_NAME))
                .skip(index)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .split(":")[0];

        long clientId = Long.parseLong(clientIdStr);
        Set<Long> contractNumbersBefore = accountRepository.getAllAccountsByClientId(clientId);
        long oldItem = (long) contractNumbersBefore.toArray()[random.nextInt(contractNumbersBefore.size())];
        long newItem = random.nextLong() & 0x7FFF_FFFF_FFFF_FFFFL;

        Set<Long> contractNumbersExpected = contractNumbersBefore.stream()
                .map(item -> item == oldItem ? newItem : item)
                .collect(Collectors.toSet());

        boolean changed = accountRepository.updateAccountInfoByClientId(clientId, oldItem, newItem);
        Set<Long> contractNumbersAfter = accountRepository.getAllAccountsByClientId(clientId);

        assertEquals(contractNumbersExpected, contractNumbersAfter);
        assertTrue(changed);
    }
}
