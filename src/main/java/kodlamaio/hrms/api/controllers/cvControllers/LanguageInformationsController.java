package kodlamaio.hrms.api.controllers.cvControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.cvServices.LanguageInformationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;
import kodlamaio.hrms.entities.dtos.cv.LanguageInformationDto;

@RestController
@RequestMapping("/api/languageInformations")
public class LanguageInformationsController {
	
	private LanguageInformationService languageInformationService;

	@Autowired
	public LanguageInformationsController(LanguageInformationService languageInformationService) {
		super();
		this.languageInformationService = languageInformationService;
	}
	
	@GetMapping("/findByCandidate")
	public DataResult<List<LanguageInformationDto>> findByCandidateId(@RequestParam int candidateId){
		return this.languageInformationService.listLanguageInformationDtosOfCandidate(candidateId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody LanguageInformation languageInformation) {
		return this.languageInformationService.add(languageInformation);
	}
	
	

}
