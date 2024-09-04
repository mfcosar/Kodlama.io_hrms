package kodlamaio.hrms.business.concretes.verifications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.verifications.EmployeeConfirmService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirm;

@Service
public class EmployeeConfirmManager implements EmployeeConfirmService{
	
	private EmployeeConfirmDao employeeConfirmDao;
	
	
	@Autowired
	public EmployeeConfirmManager(EmployeeConfirmDao employeeConfirmDao) {
		super();
		this.employeeConfirmDao = employeeConfirmDao;
		}

	@Override
	public int generateEmployeeConfirmation(Employer employer) {
		
		EmployeeConfirm employeeConfirm = new EmployeeConfirm();
		employeeConfirm.setEmployeeId(1);//default, fk olduğu için zorunlu girildi
		employeeConfirm.setConfirmed(false);
		this.employeeConfirmDao.save(employeeConfirm);
		
		//display in system and wait for personnel to check employer
		return employeeConfirm.getId();
		//return new SuccessResult("İş vereni onaylama talebi oluşturuldu");
	
	}

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
			
	}

}
