package com.example.demo.cont.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cont.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{
    
}
