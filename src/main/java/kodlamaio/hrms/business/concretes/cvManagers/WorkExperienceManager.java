package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.WorkExperienceService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.WorkExperienceDao;
import kodlamaio.hrms.entities.concretes.cv.WorkExperience;
import kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto;

@Service
public class WorkExperienceManager implements WorkExperienceService{
	
	private WorkExperienceDao workExperienceDao;
	
	@Autowired
	public WorkExperienceManager(WorkExperienceDao workExperienceDao) {
		super();
		this.workExperienceDao = workExperienceDao;
	}

	@Override
	public Result add(WorkExperience workExperience) {
		this.workExperienceDao.save(workExperience);
		return new SuccessResult("İş deneyimi eklendi.");
	}

	@Override
	public DataResult<List<WorkExperienceDto>> listWorkExperienceDtosOfCandidate(int candidateId) {
		//hala devam eden iş en üstte
		List<WorkExperienceDto> listWorkExp = this.workExperienceDao.findWorkExperienceDtoByCandidateIdAndIsContinuedTrue(candidateId);
		
		listWorkExp.addAll(this.workExperienceDao.findWorkExperienceDtoByCandidateIdOrderByEndDateDesc(candidateId));
		return new SuccessDataResult<List<WorkExperienceDto>>(listWorkExp,  "İş deneyimleri çıkış tarihine göre dizilip listelendi.");
	}

	/*@Override
	public DataResult<List<WorkExperience>> listWorkExperienceOfCandidate(int candidateId) {
		//hala devam eden iş en üstte
		List<WorkExperience> listWorkExp = this.workExperienceDao.findByCandidateIdAndIsContinuedTrue(candidateId);
		
		listWorkExp.addAll(this.workExperienceDao.findByCandidateIdOrderByEndDateDesc(candidateId));
		return new SuccessDataResult<List<WorkExperience>>(listWorkExp,  "İş deneyimleri çıkış tarihine göre dizilip listelendi.");
	}*/
}
