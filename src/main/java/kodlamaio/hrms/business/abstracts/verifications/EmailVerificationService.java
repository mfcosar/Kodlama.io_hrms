package kodlamaio.hrms.business.abstracts.verifications;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.verifications.EmailVerification;

public interface EmailVerificationService {
	
	DataResult<List<EmailVerification>> getAll();
	Result generateVerificationEmailForCandidate(Candidate candidate);
	//int generateVerificationEmailForCandidate(Candidate candidate);
	Result generateVerificationEmailForEmployer(Employer employer);
	//Result setCandidateVerificationCompleted(int candidateId);
	public Result setCandidateVerificationCompleted(String code);
	Result setEmployerVerificationCompleted(int employerId);
	Result checkCandidateEmailVerification(String code); //int candidateId
	Result checkEmployerEmailVerification(int employerId);

}
