package com.example.Event.Management.Service;


import com.example.Event.Management.Entity.CustomerData;
import com.example.Event.Management.EventRepository.CustomerDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDataService {

    @Autowired
    private CustomerDataRepository customerDataRepository;

    public List<CustomerData> getAllCustomers() {
        return customerDataRepository.findAll();
    }

    public CustomerData addCustomer(CustomerData customerData) {
        return customerDataRepository.save(customerData);
    }

    public CustomerData getCustomerById(Long id) {
        return customerDataRepository.findById(id).orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerDataRepository.deleteById(id);
    }
}
