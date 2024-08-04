package kodlamaio.hrms.api.controllers;

import kodlamaio.hrms.business.abstracts.JobService;
import kodlamaio.hrms.entities.concretes.Job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobsController {
	
	private JobService jobService;
	
	@Autowired //Spring projeyi tarayıp JobService'i implement eden class'tan instance oluşturulur
	public JobsController(JobService jobService) {
		super();
		this.jobService = jobService;
	}

	@GetMapping("/getall") // get istekleri için kullanılır
	public List<Job> getAll(){
		return this.jobService.getAll();
	}

}
