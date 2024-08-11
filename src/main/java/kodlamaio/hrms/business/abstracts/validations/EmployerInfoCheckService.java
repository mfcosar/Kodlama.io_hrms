package kodlamaio.hrms.business.abstracts.validations;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;

public interface EmployerInfoCheckService {
	
	Result checkAllFieldsEntered(Employer employer);
	Result checkWebAndEmailDomainSame(String webAddress, String email);
	Result checkEmailIsUnique(Employer employer);

}
