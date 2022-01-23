package com.example.xanderboot.springbootpostgresqlhibernatecrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	@Modifying
	@Query("update Employee set dept= :newVal where dept = :preVal")
	void updateDepartment(String preVal, String newVal);

}
