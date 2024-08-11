package kodlamaio.hrms.business.concretes.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.validations.CandidateInfoCheckService;
import kodlamaio.hrms.core.adapters.MernisAdapter;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmailVerificationDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;

@Service
public class CandidateInfoCheckManager implements CandidateInfoCheckService{
	
	private CandidateDao candidateDao;
	private MernisAdapter mernisAdapter;
	private CandidateEmailVerificationDao candidateEmailVerificationDao;
	private EmailVerificationDao emailVerificationDao;

	@Autowired
	public CandidateInfoCheckManager(CandidateDao candidateDao, MernisAdapter mernisAdapter, 
			CandidateEmailVerificationDao candidateEmailVerificationDao, EmailVerificationDao emailVerificationDao) {
		super();
		this.candidateDao = candidateDao;
		this.mernisAdapter = mernisAdapter;
		this.candidateEmailVerificationDao = candidateEmailVerificationDao;
		this.emailVerificationDao = emailVerificationDao;
	}

	@Override
	public Result checkAllFieldsEntered(Candidate candidate) {
		
		if (candidate.getFirstName().isEmpty())
			return new ErrorResult("Lütfen isminizi giriniz.");
		else if (candidate.getLastName().isEmpty())
			return new ErrorResult("Lütfen soyadınızı giriniz.");
		else if (candidate.getTcIdentityNumber().isEmpty())
			return new ErrorResult("Lütfen Tc Kimlik No giriniz.");
		else if (String.valueOf(candidate.getBirthYear()).isEmpty()) // String conversion
			return new ErrorResult("Lütfen doğum yılınızı giriniz.");
		else if (candidate.getPassword().isEmpty())
			return new ErrorResult("Lütfen şifrenizi giriniz.");
		else 
			return new SuccessResult("Bütün alanlar eksiksiz girilmiş.");
	}

	@Override
	public Result checkMernis(String tcIdentityNumber) {
		
		if (this.mernisAdapter.checkIfrealPerson(tcIdentityNumber))
			return new SuccessResult("Bu kullanıcının Mernis doğrulaması yapılmıştır.");
		else 
			return new ErrorResult("Bu kullanıcının Mernis doğrulaması yapılamamıştır.");
	}

	@Override
	public Result checkEmailAndTcIdentityIsUnique(String tcIdentityNumber, String email) {
		
		for(Candidate x: this.candidateDao.findAll()) {
			
			if ((x.getTcIdentityNumber().equals(tcIdentityNumber)) || (x.getEmail().equals(email))) {
				return new ErrorResult("Bu kullanıcı daha önce sisteme kayıt edilmiş.");
			}
		}
			return new SuccessResult("Bu kullanıcı daha önce sisteme kaydedilmemiş.");
	}

	@Override
	public Result checkEmailVerification(int candidateId) {
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
	public Result isValidCandidate(Candidate candidate) {
		
		if (checkAllFieldsEntered(candidate).isSuccess() && checkMernis(candidate.getTcIdentityNumber()).isSuccess() &&
				checkEmailAndTcIdentityIsUnique(candidate.getTcIdentityNumber(), candidate.getEmail()).isSuccess() ) {
			
			return new SuccessResult("Bu kullanıcının bilgileri kayıt için uygundur.");
		}
		else 
			return new ErrorResult("Bu kullanıcının bilgileri kayıt için uygun değildir.");
	}

}
