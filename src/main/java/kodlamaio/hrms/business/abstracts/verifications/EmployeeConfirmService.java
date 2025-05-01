package kodlamaio.hrms.business.abstracts.verifications;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;


public interface EmployeeConfirmService {

	Result generateEmployeeConfirmation(Employer employer);//Result generateEmployeeConfirmation(Employer employer);
	Result generateEmployeeConfirmationForJobadvertisement(JobAdvertisement jobAdvertisement);
	//Result confirmEmployer(int confirmationId, Employer employer, int employeeId);
	//Result checkEmployeeConfirmation(int confirmationId);//id'si henüz yok
	
}

