package kodlamaio.hrms.business.abstracts.validations;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;

public interface CandidateInfoCheckService {
	
	Result checkAllFieldsEntered(Candidate candidate);
	Result checkMernis(String tcIdentityNumber);
	Result checkEmailAndTcIdentityIsUnique(String tcIdentityNumber, String email);
	Result isValidCandidate(Candidate candidate); // All checks here
	

}
