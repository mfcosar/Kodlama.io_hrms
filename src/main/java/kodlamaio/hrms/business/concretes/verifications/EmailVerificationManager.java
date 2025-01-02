package kodlamaio.hrms.business.concretes.verifications;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message; 
import jakarta.mail.internet.InternetAddress; 
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Value("${spring.mail.password}")
	private String password;

	
	@Autowired
	public EmailVerificationManager(EmailVerificationDao emailVerificationDao, CandidateEmailVerificationDao candidateEmailVerificationDao,
			EmployerEmailVerificationDao employerEmailVerificationDao) {
		super();
		this.emailVerificationDao = emailVerificationDao;
		this.candidateEmailVerificationDao= candidateEmailVerificationDao;
		this.employerEmailVerificationDao = employerEmailVerificationDao;
	}
	
	@Override
	public String sendEmail(String emailTo, String subject, String emailBody) {
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.prot", "465");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
            	return new PasswordAuthentication(fromEmail, password);
            }
        }
        );
        
        try {
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(emailBody);
            Transport.send(message);
            return "Mail sended.";

        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
	}

	@Override
	public DataResult<List<EmailVerification>> getAll() {

		return new SuccessDataResult<List<EmailVerification>>(this.emailVerificationDao.findAll(), "Data Listelendi");
	}

	@Override
	public Result generateVerificationEmailForCandidate(Candidate candidate) {//daha DB'e kaydedilmedi: Result
	
		String code=generateNewEmailVerificationCode(38); //db'de 38 digit ama bu çok zorladı 
		CandidateEmailVerification candidateEmailVerification = 
				new CandidateEmailVerification(code, false, LocalDateTime.now().plusHours(48), candidate.getId());
		this.candidateEmailVerificationDao.save(candidateEmailVerification);
		
		String verificationLink = "http://localhost:8080/api/verifications/verifyCandidateAccount?token=" + code + "&candidateId=" + candidate.getId();
		String emailBody = "Please click the link below to verify your account:\n" + verificationLink;
		//sendEmail(candidate.getEmail(), "Account Verification", emailBody); //gamil app specific password istediği için hata verdi
		
		return new SuccessResult("Verification code is sent to user."); 
	}
	
	
	
	@Override
	public Result generateVerificationEmailForUser(User user) {//daha DB'e kaydedilmedi: Result
	
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setCode(generateNewEmailVerificationCode(38));
		emailVerification.setVerified(false);
		emailVerification.setVerificationExpiry(LocalDateTime.now().plusHours(48));
		this.emailVerificationDao.save(emailVerification);
		
		sendVerificationEmail(user);
		
		return new SuccessResult("Kullanıcıya doğrulama kodu gönderildi."); 
	}
	
	private String sendVerificationEmail(User user) {
		String verificationLink = "http://localhost:8080/api/verifications/verifyAccount?token=" + user.getId();
		String emailBody = "Please click the link below to verify your account:\n" + verificationLink;
		return sendEmail(user.getEmail(), "Account Verification", emailBody);
	}
	

	
	@Override
	public Result setUserVerificationCompleted(String code) {
		int emailVerificationId=0;

		for (EmailVerification x: this.emailVerificationDao.findAll()) {
			if (x.getCode().equals(code)) //en az 4 saattir burdaki string comparisonu == ile yapmaya çalıştığım için hatayı bulamadım.4.9.2024 2:09
				emailVerificationId = x.getId();
		}
		EmailVerification emailVerification = this.emailVerificationDao.getReferenceById(emailVerificationId);//getById ==> deprecated
		emailVerification.setVerified(true);
		//emailVerification.setVerificationDate(new Date());

		this.emailVerificationDao.save(emailVerification); //emailVerification
		
		return new SuccessResult("Email doğrulaması tamamlandı.");
	}
	

	private String generateNewEmailVerificationCode(int charCount) {
		
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
		final SecureRandom RANDOM = new SecureRandom();
		StringBuilder sb = new StringBuilder(charCount); 
		for (int i = 0; i < charCount; i++) { 
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()))); 
			} 
		return sb.toString();
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

	@Override
	public Result generateVerificationEmailForEmployer(Employer employer) {
		
		String code=generateNewEmailVerificationCode(38); //db'de 38 digit ama bu çok zorladı 
		EmployerEmailVerification employerEmailVerification = 
				new EmployerEmailVerification(code, false, LocalDateTime.now().plusHours(48), employer.getId());
		this.employerEmailVerificationDao.save(employerEmailVerification);
		
		String verificationLink = "http://localhost:8080/api/verifications/verifyEmployerAccount?token=" + code + "&employerId=" + employer.getId();
		String emailBody = "Please click the link below to verify your account:\n" + verificationLink;
		//sendEmail(candidate.getEmail(), "Account Verification", emailBody); //gamil app specific password istediği için hata verdi
		
		return new SuccessResult("Verification code is sent to user."); 
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
