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
import kodlamaio.hrms.entities.concretes.Employer;


@Service
public class EmployerManager implements EmployerService{

	private EmployerDao employerDao;
	private EmailVerificationService emailVerificationService;
	private EmployeeConfirmService employeeConfirmService;
	private EmployerInfoCheckService employerInfoCheckService;
	
	@Autowired
	public EmployerManager(EmployerDao employerDao, EmailVerificationService emailVerificationService,
			EmployeeConfirmService employeeConfirmService, EmployerInfoCheckService employerInfoCheckService) {
		super();
		this.employerDao = employerDao;
		this.emailVerificationService = emailVerificationService;
		this.employeeConfirmService = employeeConfirmService;
		this.employerInfoCheckService = employerInfoCheckService;
	}


	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(Employer employer) {
		
		if (this.employerInfoCheckService.isValidEmployer(employer).isSuccess()) {
			//send verification email
			this.emailVerificationService.generateVerificationEmailForEmployer(employer);
			//send confirmation to employees
			this.employeeConfirmService.generateEmployeeConfirmation(employer);
			
			
			// Burada şirket email doğrulaması yaptı. 
			this.emailVerificationService.setEmployerVerificationCompleted(employer.getId());

			// Burada şirket  HRMS personeli (1 nolu employeeId olan) tarafından onaylandı. 
			this.employeeConfirmService.confirmEmployer(employer, 1); 
			
			if ( this.emailVerificationService.checkEmployerEmailVerification(employer.getId()).isSuccess() &&
					this.employeeConfirmService.checkEmployeeConfirmation(employer.getId()).isSuccess()) {
				
				this.employerDao.save(employer);
				return new SuccessResult("İş veren eklendi");
				
			}else 
				return new ErrorResult("İş veren eklenmesi için email ve HRMS doğrulamasının tamamlanması gerekiyor.");
		}
		else return new ErrorResult("İş veren eklenemedi");
	}

}
