package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;
import kodlamaio.hrms.entities.dtos.cv.TechnologyAbilityDto;

public interface TechnologyAbilityService {
	
	Result add(TechnologyAbility technologyAbility);
	//DataResult<List<TechnologyAbility>> listTechnologyAbilityOfCandidate(int candidateId);
	
	//Dtos:
	DataResult<List<TechnologyAbilityDto>> listTechnologyAbilityDtosOfCandidate(int candidateId);

}
