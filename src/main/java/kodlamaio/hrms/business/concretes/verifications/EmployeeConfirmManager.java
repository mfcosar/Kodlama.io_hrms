package kodlamaio.hrms.business.concretes.verifications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.verifications.EmployeeConfirmService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmEmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirm;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;

@Service
public class EmployeeConfirmManager implements EmployeeConfirmService{
	
	private EmployeeConfirmDao employeeConfirmDao;
	private EmployeeConfirmEmployerDao employeeConfirmEmployerDao;
	
	
	@Autowired
	public EmployeeConfirmManager(EmployeeConfirmDao employeeConfirmDao,
			EmployeeConfirmEmployerDao employeeConfirmEmployerDao) {
		super();
		this.employeeConfirmDao = employeeConfirmDao;
		this.employeeConfirmEmployerDao = employeeConfirmEmployerDao;
	}

	@Override
	public Result generateEmployeeConfirmation(Employer employer) {
		
		EmployeeConfirm employeeConfirm = new EmployeeConfirm();
		this.employeeConfirmDao.save(employeeConfirm);
		
		EmployeeConfirmEmployer employeeConfirmEmployer = new EmployeeConfirmEmployer();
		employeeConfirmEmployer.setId(employeeConfirm.getId());
		employeeConfirmEmployer.setEmployerId(employer.getId());
		this.employeeConfirmEmployerDao.save(employeeConfirmEmployer);
		
		return new SuccessResult("İş vereni onaylama talebi oluşturuldu");
	
	}

	@Override
	public Result confirmEmployer(Employer employer, int employeeId) {
		int employeeConfirmId=0;
		
		for (EmployeeConfirmEmployer x: this.employeeConfirmEmployerDao.findAll()) {
			
			if(x.getEmployerId() == employer.getId())
				employeeConfirmId = x.getId();
		}
	
		EmployeeConfirm employeeConfirm = this.employeeConfirmDao.getReferenceById(employeeConfirmId);
		employeeConfirm.setConfirmed(true);
		employeeConfirm.setConfirmDate(new Date());
		employeeConfirm.setEmployeeId(employeeId);
		
		this.employeeConfirmDao.save(employeeConfirm);
		
		return new SuccessResult("İş veren onaylandı: " +  employeeId );
		
	}

	@Override
	public Result checkEmployeeConfirmation(int employerId) {
		int employeeConfirmId=0;
		
		for (EmployeeConfirmEmployer x: this.employeeConfirmEmployerDao.findAll()) {
			
			if(x.getEmployerId() == employerId)
				employeeConfirmId = x.getId();
		}
		EmployeeConfirm employeeConfirm = this.employeeConfirmDao.getReferenceById(employeeConfirmId);
		
		if (employeeConfirm.isConfirmed())
			return new SuccessResult("İş veren HRMS personeli tarafından onaylanmış. ");
		else 
			return new ErrorResult("Bu iş verenin HRMS personeli tarafından doğrulaması henüz yapılmamış.");
			
	}

}
