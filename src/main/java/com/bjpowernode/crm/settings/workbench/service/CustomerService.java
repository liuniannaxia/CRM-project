package com.bjpowernode.crm.settings.workbench.service;

import com.bjpowernode.crm.settings.workbench.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> queryCustomers(Customer customer);
}
