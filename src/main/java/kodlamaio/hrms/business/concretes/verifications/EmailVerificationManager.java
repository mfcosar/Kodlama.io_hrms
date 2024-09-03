package kodlamaio.hrms.business.concretes.verifications;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	public Result  generateVerificationEmailForCandidate(Candidate candidate) {//daha DB'e kaydedilmedi: Result
		
		/*CandidateEmailVerification candidateEmailVerification = new CandidateEmailVerification();
		candidateEmailVerification.setCode(generateNewEmailVerificationCode()+candidate.getEmail());
		candidateEmailVerification.setVerified(false);
		this.candidateEmailVerificationDao.save(candidateEmailVerification);*/
		
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode(generateNewEmailVerificationCode()+candidate.getEmail());
		emailVerification.setVerified(false);
		this.emailVerificationDao.save(emailVerification);
		
		sendVerificationEmail(candidate);
		
		return new SuccessResult("Adaya doğrulama kodu gönderildi."); //candidateEmailVerification.getId();//
	}
	
	private String generateNewEmailVerificationCode() {
		// new email verification 
		//String code = "generateRandomCodeHere0123456789" + new Date().hashCode(); //burası random string olacak ama biz simulasyon için sabit kullanıyoruz
		String code = "generateRandomCodeHere0123456789" ;
		return code;
	}
	
	private Result sendVerificationEmail(User user) {
		//send email to User
		return new SuccessResult("Doğrulama kodu email adresine gönderildi : " + user.getEmail());
	}

	@Override
	public Result setCandidateVerificationCompleted(String code) {
		int emailVerificationId=0;

		for (EmailVerification x: this.emailVerificationDao.findAll()) {
			if (x.getCode().equals(code)) //en az 8 saattir burdaki string comparisonu == ile yapmaya çalıştığım için hatayı bulamadım.4.9.2024 2:09
				emailVerificationId = x.getId();
		}
		
		EmailVerification emailVerification = this.emailVerificationDao.getReferenceById(emailVerificationId);//getById ==> deprecated
		emailVerification.setVerified(true);
		emailVerification.setVerificationDate(new Date());
		
		/*CandidateEmailVerification candidateEmailVerification = new CandidateEmailVerification();
		candidateEmailVerification.setId(emailVerificationId);*/
		
		/*CandidateEmailVerification candidateEmailVerification = this.candidateEmailVerificationDao.getReferenceById(emailVerificationId); //getById ==> deprecated
		candidateEmailVerification.setVerified(true);
		candidateEmailVerification.setVerificationDate(new Date());*/
		this.emailVerificationDao.save(emailVerification); //emailVerification
		
		return new SuccessResult("Email doğrulaması tamamlandı.");
	}
	
	@Override
	public Result checkCandidateEmailVerification(String code) { //int candidateId
		int id;
		for (EmailVerification x: this.emailVerificationDao.findAll()){
			if (x.getCode() == code) {	
				id = x.getId();
				if (this.emailVerificationDao.getReferenceById(id).isVerified())
					return new SuccessResult("Bu kullanıcının email doğrulaması yapılmış.");
			}
		}

		return new ErrorResult("Bu kullanıcının email doğrulaması henüz yapılmamış.");
	}
	
	@Override
	public Result generateVerificationEmailForEmployer(Employer employer) {
		
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode(generateNewEmailVerificationCode());
		emailVerification.setVerified(false);
		this.emailVerificationDao.save(emailVerification);
		
		EmployerEmailVerification employerEmailVerification = new EmployerEmailVerification();
		employerEmailVerification.setId(emailVerification.getId());
		employerEmailVerification.setEmployerId(employer.getId());
		this.employerEmailVerificationDao.save(employerEmailVerification);
		
		sendVerificationEmail(employer);
		
		return new SuccessResult("İş verene doğrulama kodu gönderildi.");
	}



/*	@Override
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
	}*/


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
