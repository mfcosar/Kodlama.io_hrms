package kodlamaio.hrms.business.abstracts.verifications;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.Employer;


public interface EmployeeConfirmService {

	Result generateEmployeeConfirmation(Employer employer);
	Result confirmEmployer(int employeeConfirmId, Employee employee);

}

