package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;

public interface EmployerService {
	
	DataResult<List<Employer>> getAll();
	DataResult<List<Employer>> getAllUnconfirmedEmployers();
	Result add(Employer employer);
	DataResult<Employer> getEmployerById(int employerId);
	Result update(Employer employer);

}
