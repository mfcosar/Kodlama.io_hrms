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
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService{
	
	private CandidateDao candidateDao;
	private CandidateInfoCheckService candidateInfoCheckService;
	private EmailVerificationService emailVerificationService;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, CandidateInfoCheckService candidateInfoCheckService, EmailVerificationService emailVerificationService) {
		super();
		this.candidateDao = candidateDao;
		this.candidateInfoCheckService = candidateInfoCheckService;
		this.emailVerificationService = emailVerificationService;
	}

	@Override
	public DataResult<List<Candidate>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Data Listelendi");
	}

	@Override
	public Result add(Candidate candidate) {
		
		if (this.candidateInfoCheckService.isValidCandidate(candidate).isSuccess()) {
			
			this.emailVerificationService.generateVerificationEmailForCandidate(candidate);
			
			// Burada aday email doğrulaması yaptı. 
			this.emailVerificationService.setCandidateVerificationCompleted(candidate.getId());
			
			if (this.emailVerificationService.checkCandidateEmailVerification(candidate.getId()).isSuccess()) {
				this.candidateDao.save(candidate);
				return new SuccessResult("Aday eklendi");
			} 
			else return new ErrorResult("Aday eklenmesi için email doğrulaması gerekiyor.");
		}
		else return new ErrorResult("Aday eklenemedi");

	}
	
}
