package com.example.xanderboot.springbootpostgresqlhibernatecrudexample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.model.Employee;
import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.repository.EmployeeRepository;
import com.example.xanderboot.springbootpostgresqlhibernatecrudexample.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/app/v1")
public class EmployeeController {

	
	@Autowired
	private EmployeeRepository employeRepository;
	
	//get eemployees
	@GetMapping("employees")
	public List<Employee> getAllEmployee(){
		return this.employeRepository.findAll();
	}
	
	
	//get employee by id
	@GetMapping("employee/{id}")
	public ResponseEntity<Employee> getEmployeeId(@PathVariable(value="id") Long employeeId) 
		throws ResourceNotFoundException {
			Employee employee = employeRepository.findById(employeeId)
					.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+employeeId));		
			return ResponseEntity.ok().body(employee);
	}
	
	
	//(Required) get employees from start and end values
	@GetMapping("employee/start={sid}/end={eid}")
	public List<Employee> getSelectedEmployee(@PathVariable(value="sid") Long sid, 
											  @PathVariable(value="eid") Long eid){
		return this.employeRepository.findAll();
	}
	
	
	//save employee
	@PostMapping("employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeRepository.save(employee);
	}
	
	
	//update employee
	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long employeeId,
		@Validated @RequestBody Employee employeeDetails)
				throws ResourceNotFoundException{		
		
		Employee employee = employeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+employeeId));
		employee.setEmail(employeeDetails.getEmail());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		
		return ResponseEntity.ok(this.employeRepository.save(employee));
		
	}
	
	
	//(Required) update employees by array of ids  <--Done
	@PutMapping("employeesUpdate")
	public List<Employee> updateEmployees(@RequestBody ArrayList<Employee> emp)
			throws ResourceNotFoundException{
		
		Iterator<Employee> itr = emp.iterator();
		List<Employee> employees=new ArrayList<Employee>();
		while(itr.hasNext()) {
			Employee employeeDetails = itr.next();
			System.out.println(employeeDetails.getId());
			Employee employee = employeRepository.findById(employeeDetails.getId())
					.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+employeeDetails.getId()));
			employee.setEmail(employeeDetails.getEmail());
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			this.employeRepository.save(employee);
			employees.add(employee);
		}
		
		return employees;
		
	}
	
	//delete employee
	@DeleteMapping("employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value="id") Long employeeId)
	throws ResourceNotFoundException{
		Employee employee = employeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+employeeId));
		
		this.employeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;		
		
	}
	
	
	//(Required) delete employee by array of ids
	@DeleteMapping("employeesDelete")
	public List<Map<String, Boolean>> deleteEmployees(@RequestBody ArrayList<Employee> emp)
	throws ResourceNotFoundException{
		Iterator<Employee> itr = emp.iterator();
		List<Map<String, Boolean>> employeesDelete=new ArrayList<Map<String, Boolean>>();
		
		while(itr.hasNext()) {
			Employee employeeDetails = itr.next();
			System.out.println(employeeDetails.getId());
			Employee employee = employeRepository.findById(employeeDetails.getId())
					.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+employeeDetails.getId()));
	
	
			this.employeRepository.delete(employee);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted :"+employee.getId(), Boolean.TRUE);
			employeesDelete.add(response);
		}
		return employeesDelete;		
		
	}
	
}
