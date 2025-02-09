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

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin //front end'in istek yapabilmesi için

public class CandidatesController {
	
	private CandidateService candidateService;

	@Autowired
	public CandidatesController(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;
	}
	
	@GetMapping("/getall") 
	public DataResult<List<Candidate>> getAll(){
		return this.candidateService.getAll();
	}
	
	@GetMapping("/getcandidatebyid/{candidateId}") 
	@PreAuthorize("hasRole('ADMIN') or hasRole('CANDIDATE')")
	public DataResult<Candidate> getCandidateById(@PathVariable int candidateId){//@Valid @RequestParam@RequestBody 
		System.out.println("get employer by employer id: " + candidateId);
		return this.candidateService.findCandidateById(candidateId);
	}		
	
	@PostMapping("/add")
	public Result add(@RequestBody Candidate candidate) {
		return this.candidateService.add(candidate);
	}
	
	@PutMapping("/updatecandidatebyid/{candidateId}") 
	@PreAuthorize("hasRole('ADMIN') or hasRole('CANDIDATE')")
	public Result updatecandidateById(@RequestBody Candidate candidate){//@Valid @RequestParam@RequestBody 
		System.out.println("update candidate by candidate id: " + candidate.getId());
		return this.candidateService.update(candidate);
	}		

	
}
