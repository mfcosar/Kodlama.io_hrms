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
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "*", maxAge = 3600) //front end'in istek yapabilmesi için
public class EmployersController {
	
	private EmployerService employerService;

	@Autowired
	public EmployersController(EmployerService employerService) {
		super();
		this.employerService = employerService;
	}
	
	
	@GetMapping("/getall") 
	public DataResult<List<Employer>> getAll(){
		
		return this.employerService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Employer employer) {
		return this.employerService.add(employer);
	}
	
	@GetMapping("/getemployerbyid/{employerId}") 
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<Employer> getEmployerById(@PathVariable int employerId){//@Valid @RequestParam@RequestBody 
		System.out.println("get employer by employer id: " + employerId);
		return this.employerService.getEmployerById(employerId);
	}	

	@PutMapping("/updateemployerbyid/{employerId}") 
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public Result updateEmployerById(@RequestBody Employer employer){//@Valid @RequestParam@RequestBody 
		System.out.println("update employer by employer id: " + employer.getId());
		return this.employerService.update(employer);
	}		
}
