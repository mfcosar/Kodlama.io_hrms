package kodlamaio.hrms.business.concretes.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.validations.JobInfoCheckService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobDao;
import kodlamaio.hrms.entities.concretes.Job;

@Service
public class JobInfoCheckManager implements JobInfoCheckService{
	
	private JobDao jobDao;

	@Autowired
	public JobInfoCheckManager(JobDao jobDao) {
		super();
		this.jobDao = jobDao;
	}

	@Override
	public Result isUniqueJobTitle(String jobTitle) {
		
		for(Job x: this.jobDao.findAll()) {
			if (x.getJobTitle().equalsIgnoreCase(jobTitle)) {
				return new ErrorResult("Bu iş pozisyonu sisteme kayıtlı.");
			}
		}
		return new SuccessResult("İş pozisyonu sisteme kayıtlı değil");
	}

}
