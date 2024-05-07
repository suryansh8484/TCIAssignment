package com.example.demo.cont.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cont.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long>{
    Department findByName(String name);

    
}
