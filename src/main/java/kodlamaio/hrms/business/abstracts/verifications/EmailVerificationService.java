package kodlamaio.hrms.business.abstracts.verifications;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.EmailVerification;

@Service
public interface EmailVerificationService {
	
	DataResult<List<EmailVerification>> getAll();
	Result  generateVerificationEmailForUser(User user); //int
	Result setUserVerificationCompleted(String code);
	Result checkUserEmailVerification(String code);
	String sendEmail(String email, String subject, String emailBody);
	Result generateVerificationEmailForCandidate(Candidate candidate);
	//Result addCandidateEmailVerification(int emailVerificationId, int candidateId);
	//Result addEmployerEmailVerification(int emailVerificationId, int employerId);
	/*	Result generateVerificationEmailForCandidate(Candidate candidate);
	Result generateVerificationEmailForEmployer(Employer employer);
	Result setCandidateVerificationCompleted(String code);
	Result setEmployerVerificationCompleted(int employerId);
	Result checkCandidateEmailVerification(String code); //int candidateId
	Result checkEmployerEmailVerification(int employerId);*/

}
