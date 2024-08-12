package kodlamaio.hrms.business.abstracts.validations;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;

public interface EmployerInfoCheckService {
	
	Result checkAllFieldsEntered(Employer employer);
	Result checkWebAndEmailDomainIsSame(String webAddress, String email);
	Result checkEmailIsUnique(String email);
	Result isValidEmployer(Employer employer); // All checks here

}
