package kodlamaio.hrms.api.controllers.cvControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.cvServices.WorkExperienceService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.WorkExperience;
import kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto;

@RestController
@RequestMapping("/api/workExperiences")
@CrossOrigin //front end'in istek yapabilmesi için
public class WorkExperiencesController {
	
	private WorkExperienceService workExperienceService;

	@Autowired
	public WorkExperiencesController(WorkExperienceService workExperienceService) {
		super();
		this.workExperienceService = workExperienceService;
	}
	
	@GetMapping("/findByCandidateId") 
	public DataResult<List<WorkExperienceDto>> findByCandidateId(@RequestParam int candidateId){
		return this.workExperienceService.listWorkExperienceDtosOfCandidate(candidateId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody WorkExperience workExperience) {
		return this.workExperienceService.add(workExperience);
	}

}
