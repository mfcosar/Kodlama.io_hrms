package kodlamaio.hrms.business.concretes.verifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.verifications.EmployeeConfirmService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmEmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmJobadvertisementDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmJobadvertisement;

@Service
public class EmployeeConfirmManager implements EmployeeConfirmService{
	
	private EmployeeConfirmEmployerDao employeeConfirmEmployerDao;
	private EmployeeConfirmJobadvertisementDao employeeConfirmJobadvertisementDao;
	
	@Autowired
	public EmployeeConfirmManager(EmployeeConfirmEmployerDao employeeConfirmEmployerDao,EmployeeConfirmJobadvertisementDao employeeConfirmJobadvertisementDao) {
		super();
		this.employeeConfirmEmployerDao = employeeConfirmEmployerDao;
		this.employeeConfirmJobadvertisementDao = employeeConfirmJobadvertisementDao;
		}

	@Override
	public Result generateEmployeeConfirmation(Employer employer) {
		
		EmployeeConfirmEmployer employeeConfirmEmployer = new EmployeeConfirmEmployer(employer.getId(), false);
		
		//employeeConfirmEmployer.setEmployerId(employer.getId());
		//employeeConfirmEmployer.setIsConfirmed(false);
		
    	this.employeeConfirmEmployerDao.save(employeeConfirmEmployer);
		
		//display in system and wait for personnel to check employer
		//return employeeConfirmEmployer.getId();
		return new SuccessResult("Employer confirmation request has been added to system");
	
	}
/*
	@Override
	public Result confirmEmployer(int confirmationId, Employer employer, int employeeId) {
	
		EmployeeConfirm employeeConfirm = this.employeeConfirmDao.getReferenceById(confirmationId);
		employeeConfirm.setEmployeeId(employeeId);
		employeeConfirm.setConfirmed(true);
		employeeConfirm.setConfirmDate(new Date());
		this.employeeConfirmDao.save(employeeConfirm);
		
		return new SuccessResult("İş veren onaylandı: " +  employeeId);
		
	}

	@Override
	public Result checkEmployeeConfirmation(int confirmationId) {

		EmployeeConfirm employeeConfirm = this.employeeConfirmDao.getReferenceById(confirmationId);
		
		if (employeeConfirm.isConfirmed())
			return new SuccessResult("İş veren HRMS personeli tarafından onaylanmış. ");
		else 
			return new ErrorResult("Bu iş verenin HRMS personeli tarafından doğrulaması henüz yapılmamış.");
			
	}*/

	@Override
	public Result generateEmployeeConfirmationForJobadvertisement(JobAdvertisement jobAdvertisement) {
		
		EmployeeConfirmJobadvertisement employeeConfirmJobadvertisement = new EmployeeConfirmJobadvertisement(jobAdvertisement.getId(), false);
    	this.employeeConfirmJobadvertisementDao.save(employeeConfirmJobadvertisement);
		
		return new SuccessResult("Jobadvertisement confirmation request has been added to system");
	}

}
