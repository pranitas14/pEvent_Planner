package com.example.Event.Management.EventRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Event.Management.Entity.CustomerData;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerData, Long> {
}
