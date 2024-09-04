package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.validations.EmployerInfoCheckService;
import kodlamaio.hrms.business.abstracts.verifications.EmailVerificationService;
import kodlamaio.hrms.business.abstracts.verifications.EmployeeConfirmService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Employer;


@Service
public class EmployerManager implements EmployerService{

	private EmployerDao employerDao;
	private EmailVerificationService emailVerificationService;
	private EmployeeConfirmService employeeConfirmService;
	private EmployerInfoCheckService employerInfoCheckService;
	private UserDao userDao;
	
	@Autowired
	public EmployerManager(EmployerDao employerDao, EmailVerificationService emailVerificationService,
			EmployeeConfirmService employeeConfirmService, EmployerInfoCheckService employerInfoCheckService, UserDao userDao) {
		super();
		this.employerDao = employerDao;
		this.emailVerificationService = emailVerificationService;
		this.employeeConfirmService = employeeConfirmService;
		this.employerInfoCheckService = employerInfoCheckService;
		this.userDao = userDao;
	}


	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(Employer employer) {
		
		if (this.employerInfoCheckService.isValidEmployer(employer).isSuccess()) {

			int emailVerificationId = this.emailVerificationService.generateVerificationEmailForUser(employer);

			int confirmationId = this.employeeConfirmService.generateEmployeeConfirmation(employer);
			
			// Burada şirket email doğrulaması yaptı. Simulasyon
			String verificationCode = "generateRandomCodeHere0123456789" + employer.getEmail();
			this.emailVerificationService.setUserVerificationCompleted(verificationCode);//henüz DB'e kaydeilmedi, id'si yok
			
			// Burada şirket  HRMS personeli (1 nolu employeeId olan) tarafından onaylandı. 
			this.employeeConfirmService.confirmEmployer(confirmationId , employer, 1); 
			
			if (this.emailVerificationService.checkUserEmailVerification(verificationCode).isSuccess() &&
					this.employeeConfirmService.checkEmployeeConfirmation(confirmationId).isSuccess()) {
				int employerId= this.userDao.findAll().size() + 1; //User tablosuna da insert edeceği için Id unique olmalı
				employer.setId(employerId);
				this.employerDao.save(employer);
				//this.emailVerificationService.addEmployerEmailVerification(emailVerificationId, employerId);
				return new SuccessResult("İş veren eklendi");
				
			}else 
				return new ErrorResult("İş veren eklenmesi için email ve HRMS doğrulamasının tamamlanması gerekiyor.");
		}
		else return new ErrorResult("İş veren eklenemedi");
	}

}
