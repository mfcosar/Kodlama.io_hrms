package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.EmployeeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employee;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeesController {
	
	private EmployeeService employeeService;

	@Autowired
	public EmployeesController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping("/getall") 
	public DataResult<List<Employee>> getAll(){
		
		return this.employeeService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Employee employee) {
		return this.employeeService.add(employee);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody Employee employee) {
		return this.employeeService.delete(employee.getId());
	}	
	
	@GetMapping("/getemployeebyid/{employeeId}") 
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<Employee> getEmployeeById(@PathVariable int employeeId){//@Valid @RequestParam@RequestBody 
		System.out.println("get employee by employee id: " + employeeId);
		return this.employeeService.getEmployeeById(employeeId);
	}	
	
	@PutMapping("/updateemployeebyid/{employeeId}") 
	@PreAuthorize("hasRole('ADMIN')")
	public Result updateEmployeeById(@RequestBody Employee employee){//@Valid @RequestParam@RequestBody 
		System.out.println("update employee by employee id: " + employee.getId());
		return this.employeeService.update(employee);
	}
}
