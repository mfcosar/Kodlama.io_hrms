package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.WorkExperience;
import kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto;

public interface WorkExperienceService {
	
	Result add(WorkExperience workExperience);
	//DataResult<List<WorkExperience>> listWorkExperienceOfCandidate(int candidateId);
	DataResult<List<WorkExperienceDto>> listWorkExperienceDtosOfCandidate(int candidateId);
}
