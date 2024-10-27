package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;

@RestController
@RequestMapping("/api/jobAdvertisements")
@CrossOrigin //front end'in istek yapabilmesi için
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
	public Result add(@RequestBody JobAdvertisement jobAdvertisement) { //@RequestParam
		return this.jobAdvertisementService.add(jobAdvertisement);
	}

	
	@GetMapping("/getallactive") 
	public DataResult<List<JobAdvertisementDto>> getAllActiveAdvertisements(){
		
		return this.jobAdvertisementService.findJobAdvertisementDtosActive();
	}
	
	@GetMapping("/getallactiveorderbypublicationdate") 
	public DataResult<List<JobAdvertisementDto>> findByIsActiveTrueOrderByPublicationDateAsc(){
		
		return this.jobAdvertisementService.findJobAdvertisementDtosActiveOrderByPublicationDateAsc();
	}
	
	@GetMapping("/getallactiveofacompanybyid") 
	public DataResult<List<JobAdvertisementDto>> findByEmployer_IdAndIsActiveTrue(@RequestParam int employerId){
		
		return this.jobAdvertisementService.findJobAdvertisementDtosByEmployerIdAndActive(employerId);
	}
	
	@GetMapping("/getallactiveofacompanybycompanyname") 
	public DataResult<List<JobAdvertisementDto>> findByEmployerCompanyNameAndActive(@RequestParam String companyName){
		
		return this.jobAdvertisementService.findJobAdvertisementDtosByEmployerCompanyNameAndActive(companyName);
	}
	
	@PostMapping("/deactivatebyid") 
	public DataResult<JobAdvertisement> deactiveJobAdvertisement(@RequestParam int jobAdvertisementId){
		
		return this.jobAdvertisementService.deactiveJobAdvertisement(jobAdvertisementId);
	}
	
	@GetMapping("/getalldtos") 
	public DataResult<List<JobAdvertisementDto>> getAllDtos(){
		
		return this.jobAdvertisementService.getAllJobAdvertisementDtos();
	}
	
	@GetMapping("/getbyid") 
	public DataResult<JobAdvertisement> getJobAdvertisementById(@RequestParam(name="id") int id){
		
		return this.jobAdvertisementService.findJobAdvertisementById(id);
	}
	
	@GetMapping("/getallunconfirmed") 
	public DataResult<List<JobAdvertisement>> getUnconfirmedJobAdvertisements(){
		
		return this.jobAdvertisementService.getUnconfirmedJobAdvertisements();
	}
	
	@PostMapping("/confirmbyid") 
	public DataResult<JobAdvertisement> confirmJobAdvertisement(@RequestParam int jobAdvertisementId){
		
		return this.jobAdvertisementService.confirmJobAdvertisementById(jobAdvertisementId);
	}
	
}
