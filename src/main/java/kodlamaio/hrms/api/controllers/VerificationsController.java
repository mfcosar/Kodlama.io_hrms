package kodlamaio.hrms.api.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verifications")
public class VerificationsController {
	
	@Autowired
	private UserDao userDao;
	private CandidateEmailVerificationDao candidateEmailVerificationDao;

	@GetMapping("/verifyCandidateAccount")
	public Result verifyAccount(@RequestParam("token") String code, @RequestParam("candidateId") int candidateId) {
		/*Optional<User> optionalUser = userDao.findById(candidateId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();*/
			
			Optional<CandidateEmailVerification> optCandidateEmailVerification=candidateEmailVerificationDao.findByCode(code);
			if (optCandidateEmailVerification.isPresent()) {
				CandidateEmailVerification candidateEmailVerification=optCandidateEmailVerification.get();
				if (candidateEmailVerification.getCandidateId()==candidateId) {
					if (!candidateEmailVerification.isVerified() ) {
		
						if (candidateEmailVerification.getVerificationExpiry().isAfter(LocalDateTime.now())) {
							candidateEmailVerification.setVerified(true);
							candidateEmailVerificationDao.save(candidateEmailVerification);
							
							Optional<User> optionalUser = userDao.findById(candidateId);
							if (optionalUser.isPresent()) {
								User user = optionalUser.get();
								user.setVerified(true);
								}
							return new SuccessResult("Your account has been successfully verified.");
						} else {
							return new ErrorResult("The verification link has expired!");
						}
					}
					else {
						return new ErrorResult("Your account has already been verified.");
					}
				}
				else {return new ErrorResult("Verification data is incorrect");}
		}
		return new ErrorResult("Verification failed!"); //code isnt in db
	}

}