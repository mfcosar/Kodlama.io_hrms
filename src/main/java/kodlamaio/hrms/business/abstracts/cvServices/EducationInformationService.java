package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.EducationInformation;

public interface EducationInformationService {
	
	Result add(EducationInformation educationInformation);
	DataResult<List<EducationInformation>> listEducationInformationOfCandidate(int candidateId);
	

}
