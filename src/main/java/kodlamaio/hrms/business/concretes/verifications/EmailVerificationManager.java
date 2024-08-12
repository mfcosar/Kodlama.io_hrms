package kodlamaio.hrms.business.concretes.verifications;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.verifications.EmailVerificationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployerEmailVerificationDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmployerEmailVerification;

@Service
public class EmailVerificationManager implements EmailVerificationService{
	
	private EmailVerificationDao emailVerificationDao;
	private CandidateEmailVerificationDao candidateEmailVerificationDao;
	private EmployerEmailVerificationDao employerEmailVerificationDao;
	
	
	@Autowired
	public EmailVerificationManager(EmailVerificationDao emailVerificationDao,
			CandidateEmailVerificationDao candidateEmailVerificationDao,
			EmployerEmailVerificationDao employerEmailVerificationDao) {
		super();
		this.emailVerificationDao = emailVerificationDao;
		this.candidateEmailVerificationDao = candidateEmailVerificationDao;
		this.employerEmailVerificationDao = employerEmailVerificationDao;
	}



	@Override
	public DataResult<List<EmailVerification>> getAll() {

		return new SuccessDataResult<List<EmailVerification>>(this.emailVerificationDao.findAll(), "Data Listelendi");
	}

	
	@Override
	public Result generateVerificationEmailForCandidate(Candidate candidate) {
		
		EmailVerification emailVerification = generateNewEmailVerificationCode();
		this.emailVerificationDao.save(emailVerification);
		
		CandidateEmailVerification candidateEmailVerification = new CandidateEmailVerification();
		candidateEmailVerification.setId(emailVerification.getId());
		candidateEmailVerification.setCandidateId(candidate.getId());
		this.candidateEmailVerificationDao.save(candidateEmailVerification);
		
		sendVerificationEmail(candidate);
		
		return new SuccessResult("Adaya doğrulama kodu gönderildi.");
	}
	
	private  EmailVerification generateNewEmailVerificationCode() {
		// new email verification
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode("generateRandomCodeHere01234566789");
		return emailVerification;
	}
	
	private Result sendVerificationEmail(User user) {
		//send email to User
		return new SuccessResult("Doğrulama kodu email adresine gönderildi : " + user.getEmail());
	}

	@Override
	public Result generateVerificationEmailForEmployer(Employer employer) {
		
		EmailVerification emailVerification = generateNewEmailVerificationCode();
		this.emailVerificationDao.save(emailVerification);
		
		EmployerEmailVerification employerEmailVerification = new EmployerEmailVerification();
		employerEmailVerification.setId(emailVerification.getId());
		employerEmailVerification.setEmployerId(employer.getId());
		this.employerEmailVerificationDao.save(employerEmailVerification);
		
		sendVerificationEmail(employer);
		
		return new SuccessResult("İş verene doğrulama kodu gönderildi.");
	}


	@Override
	public Result setCandidateVerificationCompleted(int candidateId) {
		
		int emailVerificationId = 0;
		
		for (CandidateEmailVerification x: this.candidateEmailVerificationDao.findAll()) {
			if (x.getCandidateId() == candidateId)
				emailVerificationId = x.getId();
		}
		
		EmailVerification emailVerification = this.emailVerificationDao.getReferenceById(emailVerificationId); //getById ==> deprecated
		emailVerification.setVerified(true);
		emailVerification.setVerificationDate(new Date());
		this.emailVerificationDao.save(emailVerification);
		return new SuccessResult("Email doğrulaması tamamlandı.");
	}

	@Override
	public Result setEmployerVerificationCompleted(int employerId) {
		
		int emailVerificationId = 0;
		
		for (EmployerEmailVerification x: this.employerEmailVerificationDao.findAll()) {
			if (x.getEmployerId() == employerId)
				emailVerificationId = x.getId();
		}
		
		EmailVerification emailVerification = this.emailVerificationDao.getReferenceById(emailVerificationId); //getById ==> deprecated
		emailVerification.setVerified(true);
		emailVerification.setVerificationDate(new Date());
		this.emailVerificationDao.save(emailVerification);
		return new SuccessResult("Email doğrulaması tamamlandı.");
	}



	@Override
	public Result checkCandidateEmailVerification(int candidateId) {
		int id;
		for (CandidateEmailVerification x: this.candidateEmailVerificationDao.findAll()){
			if (x.getCandidateId() == candidateId) {	
				id = x.getId();
				if (this.emailVerificationDao.getReferenceById(id).isVerified())
					return new SuccessResult("Bu kullanıcının email doğrulaması yapılmış.");
			}
		}

		return new ErrorResult("Bu kullanıcının email doğrulaması henüz yapılmamış.");
	}



	@Override
	public Result checkEmployerEmailVerification(int employerId) {
		int id;
		for (EmployerEmailVerification x: this.employerEmailVerificationDao.findAll()){
			if (x.getEmployerId() == employerId) {	
				id = x.getId();
				if (this.emailVerificationDao.getReferenceById(id).isVerified())
					return new SuccessResult("Bu kullanıcının email doğrulaması yapılmış.");
			}
		}

		return new ErrorResult("Bu kullanıcının email doğrulaması henüz yapılmamış.");
	}

}
