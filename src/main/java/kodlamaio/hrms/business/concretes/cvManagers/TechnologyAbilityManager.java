package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.TechnologyAbilityService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.TechnologyAbilityDao;
import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;

@Service
public class TechnologyAbilityManager implements TechnologyAbilityService{
	
	private TechnologyAbilityDao technologyAbilityDao;

	@Autowired
	public TechnologyAbilityManager(TechnologyAbilityDao technologyAbilityDao) {
		super();
		this.technologyAbilityDao = technologyAbilityDao;
	}

	@Override
	public Result add(TechnologyAbility technologyAbility) {
		this.technologyAbilityDao.save(technologyAbility);
		return new SuccessResult("Adaya ait teknoloji bilgisi eklendi");
	}

	@Override
	public DataResult<List<TechnologyAbility>> listTechnologyAbilityOfCandidate(int candidateId) {
		
		return new SuccessDataResult<List<TechnologyAbility>>(this.technologyAbilityDao.findByCandidateId(candidateId), 
				"Adaya ait teknoloji bilgileri listelendi");
	}

}
