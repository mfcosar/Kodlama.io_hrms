package kodlamaio.hrms.business.abstracts.validations;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;

public interface CandidateInfoCheckService {
	
	Result checkAllFieldsEntered(Candidate candidate);
	Result checkMernis(Candidate candidate);
	Result checkEmailAndTcIdentityIsUnique(Candidate candidate);
	Result checkEmailVerification(Candidate candidate);
	Result isValidUser(Candidate candidate);
	

}
