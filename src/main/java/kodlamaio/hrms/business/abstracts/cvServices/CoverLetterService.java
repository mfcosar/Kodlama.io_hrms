package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.CoverLetter;
import kodlamaio.hrms.entities.dtos.cv.CoverLetterDto;

public interface CoverLetterService {
	
	Result add(CoverLetter coverLetter);
	//DataResult<List<CoverLetter>> listCoverLetterOfCandidate(int candidateId);
	//Dtos:
	DataResult<List<CoverLetterDto>> listCoverLetterDtosOfCandidate(int candidateId);

}
