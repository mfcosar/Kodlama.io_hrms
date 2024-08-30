package kodlamaio.hrms.api.controllers.cvControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.cvServices.TechnologyAbilityService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;

@RestController
@RequestMapping("/api/technologyAbilities")
public class TechnologyAbilitiesController {
	
	private TechnologyAbilityService technologyAbilityService;
	@Autowired
	public TechnologyAbilitiesController(TechnologyAbilityService technologyAbilityService) {
		super();
		this.technologyAbilityService = technologyAbilityService;
	}

	@GetMapping("/findByCandidateId") 
	public DataResult<List<TechnologyAbility>> findByCandidateId(@RequestParam int candidateId){
		return this.technologyAbilityService.listTechnologyAbilityOfCandidate(candidateId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody TechnologyAbility technologyAbility) {
		return this.technologyAbilityService.add(technologyAbility);
	}
	

}