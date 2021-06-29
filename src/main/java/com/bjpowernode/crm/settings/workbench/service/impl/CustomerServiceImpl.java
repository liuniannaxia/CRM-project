package com.bjpowernode.crm.settings.workbench.service.impl;

import com.bjpowernode.crm.settings.workbench.domain.Customer;
import com.bjpowernode.crm.settings.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.settings.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public List<Customer> queryCustomers(Customer customer) {
        return customerMapper.selectAll();
    }
}
