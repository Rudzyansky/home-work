package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Customer;
import lombok.NonNull;

import java.util.List;

public interface CustomerRepository {

    boolean createCustomer(@NonNull String userName, String eMail);

    boolean updateCustomerEMailByUserName(@NonNull String userName, String eMail);

    boolean isCustomerExists(@NonNull String userName);

    List<Customer> getAll();

}
