package com.sbrf.reboot.repository;

import java.util.Set;

public interface AccountRepository {
    Set<Long> getAllAccountsByClientId(long clientId);

    Set<Long> getAllCardsByAccount(long contractNumber);

    /**
     * Replace contract number by client id
     * @return true in case of success or false in case if contract number not found
     */
    boolean updateAccountInfoByClientId(long clientId, long oldContractNumber, long newContractNumber);
}
