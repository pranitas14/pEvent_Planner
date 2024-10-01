package com.example.Event.Management.Controller;

import com.example.Event.Management.Entity.CustomerData;
import com.example.Event.Management.Service.CustomerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerDataController {

    @Autowired
    private CustomerDataService customerDataService;

    @GetMapping
    public List<CustomerData> getAllCustomers() {
        return customerDataService.getAllCustomers();
    }

    @PostMapping
    public CustomerData createCustomer(@RequestBody CustomerData customerData) {
        return customerDataService.addCustomer(customerData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerData> getCustomerById(@PathVariable Long id) {
        CustomerData customer = customerDataService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerDataService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
