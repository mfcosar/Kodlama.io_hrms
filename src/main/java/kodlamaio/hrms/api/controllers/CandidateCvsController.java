package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.CandidateCvService;
import kodlamaio.hrms.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/candidateCvs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CandidateCvsController {
	
	private CandidateCvService candidateCvService;

	@Autowired
	public CandidateCvsController(CandidateCvService candidateCvService) {
		super();
		this.candidateCvService = candidateCvService;

	}
	
	@GetMapping("/getCvOfCandidate") 
	DataResult<List<Object>> getCandidateCvByCandidateId(@RequestParam int candidateId){
		
		return this.candidateCvService.getCandidateCvByCandidateId(candidateId);
	}
	
}
