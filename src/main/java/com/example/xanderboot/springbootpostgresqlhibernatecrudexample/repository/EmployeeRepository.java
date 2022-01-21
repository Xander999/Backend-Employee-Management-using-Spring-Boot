package com.example.xanderboot.springbootpostgresqlhibernatecrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
