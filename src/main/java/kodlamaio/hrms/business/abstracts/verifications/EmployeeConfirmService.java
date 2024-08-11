package kodlamaio.hrms.business.abstracts.verifications;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirm;


public interface EmployeeConfirmService {

	Result sendEmployeeConfirmation(Employer employer);
	Result confirmEmployer(EmployeeConfirm employeeConfirm, Employee employee);
	EmployeeConfirm getOne(int employerId);
}

