package kodlamaio.hrms.api.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmEmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployerEmailVerificationDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeEmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmployerEmailVerification;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verifications")
public class VerificationsController { //email verification link geldiginde handle edecek API
	
	@Autowired
	private UserDao userDao;
	private CandidateEmailVerificationDao candidateEmailVerificationDao;
	private EmployerEmailVerificationDao employerEmailVerificationDao;
	private EmployeeEmailVerificationDao employeeEmailVerificationDao;
	private EmployeeConfirmEmployerDao employeeConfirmEmployerDao;
	private EmployerDao employerDao;
	
	
	@Autowired
	public VerificationsController(UserDao userDao, CandidateEmailVerificationDao candidateEmailVerificationDao,
			EmployerEmailVerificationDao employerEmailVerificationDao, EmployeeConfirmEmployerDao employeeConfirmEmployerDao, 
			EmployerDao employerDao, EmployeeEmailVerificationDao employeeEmailVerificationDao) {
		super();
		this.userDao = userDao;
		this.candidateEmailVerificationDao = candidateEmailVerificationDao;
		this.employerEmailVerificationDao = employerEmailVerificationDao;
		this.employeeEmailVerificationDao = employeeEmailVerificationDao;
		this.employeeConfirmEmployerDao = employeeConfirmEmployerDao;
		this.employerDao = employerDao;
	}



	@GetMapping("/verifyCandidateAccount")    //email verification oldugu için pre-authorization gerekmez
	public Result verifyCandidateAccount(@RequestParam("token") String code, @RequestParam("candidateId") int candidateId) {
			
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
								userDao.save(user);							
								return new SuccessResult("Your account has been successfully verified.");
							}else 
								return new ErrorResult("User verification is not complete!");
							
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
	
	@GetMapping("/verifyEmployerAccount")
	public Result verifyEmployerAccount(@RequestParam("token") String code, @RequestParam("employerId") int employerId) {
			
			Optional<EmployerEmailVerification> optEmployerEmailVerification=employerEmailVerificationDao.findByCode(code);
			if (optEmployerEmailVerification.isPresent()) {
				EmployerEmailVerification employerEmailVerification=optEmployerEmailVerification.get();
				if (employerEmailVerification.getEmployerId()==employerId) {
					if (!employerEmailVerification.isVerified() ) {
		
						if (employerEmailVerification.getVerificationExpiry().isAfter(LocalDateTime.now())) {
							employerEmailVerification.setVerified(true);
							employerEmailVerificationDao.save(employerEmailVerification);
							
							Optional<User> optionalUser = userDao.findById(employerId);
							if (optionalUser.isPresent()) {
								User user = optionalUser.get();
								user.setVerified(true);
								userDao.save(user);							
								return new SuccessResult("Your account has been successfully verified.");
							}else 
								return new ErrorResult("User verification is not complete!");
							
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
	
	@GetMapping("/verifyEmployeeAccount")
	public Result verifyEmployeeAccount(@RequestParam("token") String code, @RequestParam("employeeId") int employeeId) {
			
			Optional<EmployeeEmailVerification> optEmployeeEmailVerification=employeeEmailVerificationDao.findByCode(code);
			if (optEmployeeEmailVerification.isPresent()) {
				EmployeeEmailVerification employeeEmailVerification=optEmployeeEmailVerification.get();
				if (employeeEmailVerification.getEmployeeId()==employeeId) {
					if (!employeeEmailVerification.isVerified() ) {
		
						if (employeeEmailVerification.getVerificationExpiry().isAfter(LocalDateTime.now())) {
							employeeEmailVerification.setVerified(true);
							employeeEmailVerificationDao.save(employeeEmailVerification);
							
							Optional<User> optionalUser = userDao.findById(employeeId);
							if (optionalUser.isPresent()) {
								User user = optionalUser.get();
								user.setVerified(true);
								userDao.save(user);							
								return new SuccessResult("Your account has been successfully verified.");
							}else 
								return new ErrorResult("User verification is not complete!");
							
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
	
	@GetMapping("/employeeConfirmEmployer")
	@PreAuthorize("hasRole('ADMIN')") //Put ve post ile gönderince Admin rolünü görmedi!
	public Result employeeConfirmEmployerAccount(@RequestParam("employeeId") int employeeId, @RequestParam("employerId") int employerId) {
			
		Optional<EmployeeConfirmEmployer> optEmployeeConfirmEmployer=employeeConfirmEmployerDao.findByEmployerId(employerId);
		if (optEmployeeConfirmEmployer.isPresent()) {
			EmployeeConfirmEmployer employeeConfirmEmployer= optEmployeeConfirmEmployer.get();
			
			if (employeeConfirmEmployer.getIsConfirmed()) {
				return new ErrorResult("Employer confirmation has already been done!");
			} 
			else {
				employeeConfirmEmployer.setEmployeeId(employeeId); //burda hata vermedi
				employeeConfirmEmployer.setIsConfirmed(true);
				employeeConfirmEmployer.setConfirmDate(LocalDateTime.now());
				employeeConfirmEmployerDao.save(employeeConfirmEmployer);
				
				//find the employer and set it as employeeConfirmed
				//Optional<Employer> optionalEmployer = employerDao.findById(employerId);
				Employer optionalEmployer = employerDao.findById(employerId);
				if (optionalEmployer != null) {
					Employer employer = optionalEmployer;//.get();
					employer.setEmployeeConfirmed(true); 
					employerDao.save(employer);							
					return new SuccessResult("Employer confirmation is completed!");
				}else 
					return new ErrorResult("Employer confirmation is not complete!");
			}
			
		 } 
		else 
			return new ErrorResult("Confirmation record for this employer is missing!");
	}

}