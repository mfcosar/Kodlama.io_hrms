package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;

public interface LanguageInformationService {
	
	Result add(LanguageInformation LanguageInformation);
	DataResult<List<LanguageInformation>> listLanguageInformationOfCandidate(int candidateId);

}
