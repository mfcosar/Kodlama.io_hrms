package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
//import kodlamaio.hrms.business.abstracts.validations.CandidateInfoCheckService;
import kodlamaio.hrms.business.abstracts.verifications.EmailVerificationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService{
	
	private CandidateDao candidateDao;
	//private CandidateInfoCheckService candidateInfoCheckService;
	private EmailVerificationService emailVerificationService;
	private UserDao userDao;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserDao userDao, EmailVerificationService emailVerificationService) {
		super();
		this.candidateDao = candidateDao;
		this.emailVerificationService = emailVerificationService;
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<Candidate>> getAll() {

		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Data Listelendi");
	}

	@Override
	public Result add(Candidate candidate) {
		
		if (userDao.existsByUsername(candidate.getUsername())) {
			return new ErrorResult("Username is already registered.");
		}
		
		if (userDao.existsByEmail(candidate.getEmail())) {
			return new ErrorResult("Email is already registered.");
		}		
		
		if (candidateDao.existsByTcIdentityNumber(candidate.getTcIdentityNumber())) {
			return new ErrorResult("Tc Identity Number is already registered.");
		}	
		
		this.candidateDao.save(candidate); //candidateId olmadan generate cagrılırsa db hata veriyor
		emailVerificationService.generateVerificationEmailForCandidate(candidate);

		return new SuccessResult("You are registered successfully.\n Please check your email to verify your account in 48 hours.");
/*
		if (this.candidateInfoCheckService.isValidCandidate(candidate).isSuccess()) {
			
			//verification daha sonra implement edilecek.
			
			this.emailVerificationService.generateVerificationEmailForUser(candidate);
			
			// Burada aday email doğrulaması yaptı, emailinede gelen linke tıkladı, linkteki random string alındı 
			//simulasyon:						
			String verificationCode = "generateRandomCodeHere0123456789" + candidate.getEmail();
			
			this.emailVerificationService.setUserVerificationCompleted(verificationCode);

			if (this.emailVerificationService.checkUserEmailVerification(verificationCode).isSuccess()) {
				int candidateId= this.userDao.findAll().size() + 1; //User tablosuna da insert edeceği için Id unique olmalı
				candidate.setId(candidateId);
				this.candidateDao.save(candidate); 
				//this.emailVerificationService.addCandidateEmailVerification(emailVerificationId, candidateId);
				return new SuccessResult("Aday eklendi");
			}
			else return new ErrorResult("Aday eklenmesi için email doğrulaması gerekiyor.");
			 
		}
		else return new ErrorResult("Aday eklenemedi");
		*/
		//this.candidateDao.save(candidate);
		//return new SuccessResult("Aday eklendi");
	}
	

	@Override
	public DataResult<Candidate> findCandidateById(int candidateId) {
		return new SuccessDataResult<Candidate>(this.candidateDao.findCandidateById(candidateId), "Candidate data is listed");
	}
	
	@Override
	public Result update(Candidate candidateUpdated) {
		
		Candidate exCandidate = candidateDao.findCandidateById(candidateUpdated.getId());
		
		//if user name changed..check
		if (!exCandidate.getUsername().equals((candidateUpdated).getUsername())) {
			
			if (candidateDao.existsByUsername(candidateUpdated.getUsername()))
				return new ErrorResult("User name is already in use!");
			else { 
				System.out.print("Candidate username/email is changed to: " + candidateUpdated.getUsername());
				exCandidate.setUsername(candidateUpdated.getUsername());
				exCandidate.setEmail(candidateUpdated.getEmail());
			}
		}
		
		exCandidate.setFirstName(candidateUpdated.getFirstName());
		exCandidate.setLastName(candidateUpdated.getLastName());
		exCandidate.setTcIdentityNumber(candidateUpdated.getTcIdentityNumber());
		exCandidate.setBirthYear(candidateUpdated.getBirthYear());

		candidateDao.save(exCandidate);
		
		return new SuccessResult("Candidate profile updated succesfully");
	}	
}
