package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kodlamaio.hrms.business.abstracts.WorkingTimeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.WorkingTime;

@RestController
@RequestMapping("/api/workingtimes")
@CrossOrigin(origins = "*", maxAge = 3600)//front end'in istek yapabilmesi için
public class WorkingTimesController {
	
	private WorkingTimeService workingTimeService;

	@Autowired
	public WorkingTimesController(WorkingTimeService workingTimeService) {
		super();
		this.workingTimeService = workingTimeService;
	}
	
	@GetMapping("/getall") 
	public DataResult<List<WorkingTime>> getAll(){
		
		return this.workingTimeService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody WorkingTime workingTime) {
		return this.workingTimeService.add(workingTime);
	}

}
