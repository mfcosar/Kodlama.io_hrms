package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.validations.CandidateInfoCheckService;
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
	private CandidateInfoCheckService candidateInfoCheckService;
	private EmailVerificationService emailVerificationService;
	private UserDao userDao;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserDao userDao, CandidateInfoCheckService candidateInfoCheckService, EmailVerificationService emailVerificationService) {
		super();
		this.candidateDao = candidateDao;
		this.candidateInfoCheckService = candidateInfoCheckService;
		this.emailVerificationService = emailVerificationService;
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<Candidate>> getAll() {

		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Data Listelendi");
	}

	@Override
	public Result add(Candidate candidate) {
		
		if (this.candidateInfoCheckService.isValidCandidate(candidate).isSuccess()) {
			
			this.emailVerificationService.generateVerificationEmailForUser(candidate);
			
			// Burada aday email doğrulaması yaptı, emailinede gelen linke tıkladı, linkteki random string alındı 
			//simulasyon:						
			String verificationCode = "generateRandomCodeHere0123456789" + candidate.getEmail();
			
			this.emailVerificationService.setUserVerificationCompleted(verificationCode);

			if (this.emailVerificationService.checkUserEmailVerification(verificationCode).isSuccess()) {
				int x= this.userDao.findAll().size() + 1; //User tablosuna da insert edeceği için Id unique olmalı
				candidate.setId(x);
				this.candidateDao.save(candidate); 
				
				return new SuccessResult("Aday eklendi");
			}
			else return new ErrorResult("Aday eklenmesi için email doğrulaması gerekiyor.");
			 
		}
		else return new ErrorResult("Aday eklenemedi");

	}

	@Override
	public DataResult<Candidate> findCandidateById(int candidateId) {
		return new SuccessDataResult<Candidate>(this.candidateDao.findCandidateById(candidateId), "Aday bilgisi listelendi");
	}
	
}
