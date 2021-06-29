package com.bjpowernode.crm.settings.workbench.controller;

import com.bjpowernode.crm.settings.workbench.domain.Customer;
import com.bjpowernode.crm.settings.workbench.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/queryCustomer")
    @ResponseBody
    public List<Customer> list(@RequestParam(defaultValue = "1") int page, int pageSize, Customer customer) {
        PageHelper.startPage(page, pageSize);
        List<Customer> customers = customerService.queryCustomers(customer);
        //作用是生成下一页，第几页..
        //PageInfo<Customer> pageInfo = new PageInfo<Customer>;
        return customers;
    }



}
