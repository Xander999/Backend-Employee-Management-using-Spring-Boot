package com.example.xanderboot.springbootpostgresqlhibernatecrudexample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.model.Employee;

@Repository
public interface EmployeeRepoPagination extends PagingAndSortingRepository<Employee, Long> {

}
