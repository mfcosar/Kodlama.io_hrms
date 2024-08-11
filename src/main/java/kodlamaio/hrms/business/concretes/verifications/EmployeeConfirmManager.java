package kodlamaio.hrms.business.concretes.verifications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.verifications.EmployeeConfirmService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmEmployerDao;
import kodlamaio.hrms.entities.concretes.Employee;
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
	public Result confirmEmployer(int employeeConfirmId, Employee employee) {
		
		EmployeeConfirm employeeConfirm = this.employeeConfirmDao.getReferenceById(employeeConfirmId);
		employeeConfirm.setConfirmed(true);
		employeeConfirm.setConfirmDate(new Date());
		employeeConfirm.setEmployeeId(employee.getId());
		
		this.employeeConfirmDao.save(employeeConfirm);
		
		return new SuccessResult("İş veren onaylandı: " +employee.getFirstName() );
		
	}

}
