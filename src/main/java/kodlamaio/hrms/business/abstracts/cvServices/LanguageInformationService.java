package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;
import kodlamaio.hrms.entities.dtos.cv.LanguageInformationDto;

public interface LanguageInformationService {
	
	Result add(LanguageInformation LanguageInformation);
	//DataResult<List<LanguageInformation>> listLanguageInformationOfCandidate(int candidateId);
	//Dtos:
	DataResult<List<LanguageInformationDto>> listLanguageInformationDtosOfCandidate(int candidateId);
		
}
