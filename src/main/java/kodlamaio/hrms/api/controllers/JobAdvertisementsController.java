package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;

@RestController
@RequestMapping("/api/jobAdvertisements")
public class JobAdvertisementsController {
	
	private JobAdvertisementService jobAdvertisementService;

	@Autowired
	public JobAdvertisementsController(JobAdvertisementService jobAdvertisementService) {
		super();
		this.jobAdvertisementService = jobAdvertisementService;
	}
	
	@GetMapping("/getall") 
	public DataResult<List<JobAdvertisement>> getAll(){
		
		return this.jobAdvertisementService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody JobAdvertisement jobAdvertisement) {
		return this.jobAdvertisementService.add(jobAdvertisement);
	}

	
	@GetMapping("/getallactive") 
	public DataResult<List<JobAdvertisement>> getAllActiveAdvertisements(){
		
		return this.jobAdvertisementService.findByIsActiveTrue();
	}
	
	@GetMapping("/getallactiveorderbypublicationdate") 
	public DataResult<List<JobAdvertisement>> findByIsActiveTrueOrderByPublicationDateAsc(){
		
		return this.jobAdvertisementService.findByIsActiveTrueOrderByPublicationDateAsc();
	}
	
	@GetMapping("/getallactiveofacompanybyid") 
	public DataResult<List<JobAdvertisement>> findByEmployer_IdAndIsActiveTrue(@RequestParam int employerId){
		
		return this.jobAdvertisementService.findByEmployer_IdAndIsActiveTrue(employerId);
	}
	
	@GetMapping("/getallactiveofacompanybycompanyname") 
	public DataResult<List<JobAdvertisement>> findByEmployer_CompanyNameAndIsActiveTrue(@RequestParam String companyName){
		
		return this.jobAdvertisementService.findByEmployer_CompanyNameAndIsActiveTrue(companyName);
	}
	
	@GetMapping("/deactivatebyid") 
	public DataResult<JobAdvertisement> deactiveJobAdvertisement(@RequestParam int jobAdvertisementId){
		
		return this.jobAdvertisementService.deactiveJobAdvertisement(jobAdvertisementId);
	}
	
}
