package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kodlamaio.hrms.business.abstracts.WorkingTypeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.WorkingType;

@RestController
@RequestMapping("/api/workingtypes")
@CrossOrigin(origins = "*", maxAge = 3600) //front end'in istek yapabilmesi için
public class WorkingTypesController {

	private WorkingTypeService workingTypeService;

	@Autowired
	public WorkingTypesController(WorkingTypeService workingTypeService) {
		super();
		this.workingTypeService = workingTypeService;
	}
	
	@GetMapping("/getall") 
	public DataResult<List<WorkingType>> getAll(){
		
		return this.workingTypeService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody WorkingType workingType) {
		return this.workingTypeService.add(workingType);
	}
	
}
