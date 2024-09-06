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
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmailVerificationDao;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.EmailVerification;


@Service
public class EmailVerificationManager implements EmailVerificationService{
	
	private EmailVerificationDao emailVerificationDao;

	
	@Autowired
	public EmailVerificationManager(EmailVerificationDao emailVerificationDao) {
		super();
		this.emailVerificationDao = emailVerificationDao;
	}

	@Override
	public DataResult<List<EmailVerification>> getAll() {

		return new SuccessDataResult<List<EmailVerification>>(this.emailVerificationDao.findAll(), "Data Listelendi");
	}

	@Override
	public Result generateVerificationEmailForUser(User user) {//daha DB'e kaydedilmedi: Result
	
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode(generateNewEmailVerificationCode()+user.getEmail());
		emailVerification.setVerified(false);
		this.emailVerificationDao.save(emailVerification);
		
		sendVerificationEmail(user);
		//return emailVerification.getId();
		return new SuccessResult("Kullanıcıya doğrulama kodu gönderildi."); 
	}
	

	/*@Override
	 * denendi: DB'de fazladan satır ekliyor emailVerification tablosuna. çözülemedi
	 * 
	public Result addCandidateEmailVerification(int emailVerificationId, int candidateId) {
		CandidateEmailVerification candidateEmailVerification = this.candidateEmailVerificationDao.getReferenceById(emailVerificationId);
		candidateEmailVerification.setCandidateId(candidateId);
		this.candidateEmailVerificationDao.save(candidateEmailVerification);
		return new SuccessResult("Aday email doğrulaması kaydı tamamlandı.");
	}
	
	@Override
	public Result addEmployerEmailVerification(int emailVerificationId, int employerId) {
		EmployerEmailVerification employerEmailVerification = new EmployerEmailVerification();
		employerEmailVerification.setId(emailVerificationId);
		employerEmailVerification.setEmployerId(employerId);
		this.employerEmailVerificationDao.save(employerEmailVerification);
		return new SuccessResult("İş veren email doğrulaması kaydı tamamlandı.");
	}*/
	
	@Override
	public Result setUserVerificationCompleted(String code) {
		int emailVerificationId=0;

		for (EmailVerification x: this.emailVerificationDao.findAll()) {
			if (x.getCode().equals(code)) //en az 4 saattir burdaki string comparisonu == ile yapmaya çalıştığım için hatayı bulamadım.4.9.2024 2:09
				emailVerificationId = x.getId();
		}
		EmailVerification emailVerification = this.emailVerificationDao.getReferenceById(emailVerificationId);//getById ==> deprecated
		emailVerification.setVerified(true);
		emailVerification.setVerificationDate(new Date());

		this.emailVerificationDao.save(emailVerification); //emailVerification
		
		return new SuccessResult("Email doğrulaması tamamlandı.");
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
	public Result checkUserEmailVerification(String code) { 
		int id;
		for (EmailVerification x: this.emailVerificationDao.findAll()){
			if (x.getCode().equals(code)) {	
				id = x.getId();
				if (this.emailVerificationDao.getReferenceById(id).isVerified())
					return new SuccessResult("Bu kullanıcının email doğrulaması yapılmış.");
			}
		}

		return new ErrorResult("Bu kullanıcının email doğrulaması henüz yapılmamış.");
	}	
	/*@Override
	public Result  generateVerificationEmailForCandidate(Candidate candidate) {//daha DB'e kaydedilmedi: Result
	
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode(generateNewEmailVerificationCode()+candidate.getEmail());
		emailVerification.setVerified(false);
		this.emailVerificationDao.save(emailVerification);
		
		sendVerificationEmail(candidate);
		
		return new SuccessResult("Adaya doğrulama kodu gönderildi."); //candidateEmailVerification.getId();//
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
		
	
		this.emailVerificationDao.save(emailVerification); //emailVerification
		
		return new SuccessResult("Email doğrulaması tamamlandı.");
	}
	
	@Override
	public Result checkCandidateEmailVerification(String code) { //int candidateId
		int id;
		for (EmailVerification x: this.emailVerificationDao.findAll()){
			if (x.getCode().equals(code)) {	
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
*/

}
