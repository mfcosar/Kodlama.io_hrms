package kodlamaio.hrms.business.abstracts.verifications;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;


public interface EmployeeConfirmService {

	Result generateEmployeeConfirmation(Employer employer);
	Result confirmEmployer(Employer employer, int employeeId);
	Result checkEmployeeConfirmation(int employerId);

}

