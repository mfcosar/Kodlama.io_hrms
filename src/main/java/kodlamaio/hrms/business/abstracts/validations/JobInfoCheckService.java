package kodlamaio.hrms.business.abstracts.validations;

import kodlamaio.hrms.core.utilities.results.Result;

public interface JobInfoCheckService {
	
	Result isUniqueJobTitle(String jobTitle);

}
