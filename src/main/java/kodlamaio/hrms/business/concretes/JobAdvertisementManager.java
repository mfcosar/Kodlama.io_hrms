package kodlamaio.hrms.business.concretes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CityDao;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlamaio.hrms.dataAccess.abstracts.JobDao;
import kodlamaio.hrms.dataAccess.abstracts.WorkingTimeDao;
import kodlamaio.hrms.dataAccess.abstracts.WorkingTypeDao;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.concretes.WorkingTime;
import kodlamaio.hrms.entities.concretes.WorkingType;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;
import kodlamaio.hrms.entities.concretes.City;
import kodlamaio.hrms.entities.concretes.Job;

@Service
public class JobAdvertisementManager implements JobAdvertisementService{
	
	private JobAdvertisementDao jobAdvertisementDao;
	private JobDao jobDao;
	private CityDao cityDao;
	private WorkingTimeDao workingTimeDao;
	private WorkingTypeDao workingTypeDao;

	@Autowired
	public JobAdvertisementManager(JobAdvertisementDao jobAdvertisementDao, JobDao jobDao, CityDao cityDao,
			WorkingTimeDao workingTimeDao, WorkingTypeDao workingTypeDao) {
		super();
		this.jobAdvertisementDao = jobAdvertisementDao;
		this.jobDao = jobDao;
		this.cityDao = cityDao;
		this.workingTimeDao = workingTimeDao;
		this.workingTypeDao = workingTypeDao;
	}

	@Override
	public DataResult<List<JobAdvertisement>> getAll() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(JobAdvertisement jobAdvertisement) {
		this.jobAdvertisementDao.save(jobAdvertisement);
		return new SuccessResult("Job advertisement is added to system");
	}

	@Override
	public DataResult<JobAdvertisement> deactiveJobAdvertisement(int jobAdvertisementId) {
		
		JobAdvertisement ja = this.jobAdvertisementDao.findById(jobAdvertisementId);
		ja.setActive(false);
		this.jobAdvertisementDao.save(ja); //Db'nin save etmesi gerek
		return new SuccessDataResult<JobAdvertisement>(this.jobAdvertisementDao.findById(jobAdvertisementId), jobAdvertisementId+ " : Nolu iş ilanı pasifleştirildi");
	}

	@Override
	public DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosActive() {
		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.findJobAdvertisementDtosActive(), "Tüm aktif iş ilanları listelendi");
	}

	@Override
	public DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosActiveOrderByPublicationDateAsc() {

		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.findJobAdvertisementDtosActiveOrderByPublicationDateAsc(), "Tüm aktif iş ilanları yayınlanma tarihine göre dizilip listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerIdAndActive(int employerId) {
		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.findJobAdvertisementDtosByEmployerIdAndActive(employerId), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerCompanyNameAndActive(String companyName) {
		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.findJobAdvertisementDtosByEmployerCompanyNameAndActive(companyName), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisementDto>> getAllJobAdvertisementDtos() {
		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.getAllJobAdvertisementDtos(), "Sistemdeki tüm iş ilanları listelendi");
	}


	
	@Override
	public DataResult<JobAdvertisement> findJobAdvertisementById(int jobAdvertisementId) {

		return new SuccessDataResult<JobAdvertisement>(this.jobAdvertisementDao.findById(jobAdvertisementId), jobAdvertisementId+ " : Nolu iş ilanı listelendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getUnconfirmedJobAdvertisements() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findByIsConfirmedFalseOrderById(), "Sistemdeki tüm onaylanmamış iş ilanları listelendi");
	}

	@Override
	public DataResult<JobAdvertisement> confirmJobAdvertisementById(int jobAdvertisementId) {
		JobAdvertisement ja = this.jobAdvertisementDao.findById(jobAdvertisementId);
		ja.setConfirmed(true);
		this.jobAdvertisementDao.save(ja); //Db'nin save etmesi gerek
		return new SuccessDataResult<JobAdvertisement>(this.jobAdvertisementDao.findById(jobAdvertisementId), jobAdvertisementId+ " : Nolu iş ilanı onaylandı");

	}

	@Override
	public DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerId(int employerId) {
		return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.findJobAdvertisementDtosByEmployerId(employerId), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisement>> findJobAdvertisementsByEmployerId(int employerId) {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findJobAdvertisementsByEmployerId(employerId), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public Result update(JobAdvertisement jobAdvertisementUpdated) {
		
		JobAdvertisement exJobAdvertisement = jobAdvertisementDao.findById(jobAdvertisementUpdated.getId());
		
		//if user name changed..check
		/*if (!exEmployer.getUsername().equals((jobAdvertisementUpdated).getUsername())) {
			
			if (employerDao.existsByUsername(employerUpdated.getUsername()))
				return new ErrorResult("User name is already in use!");
			else { 
				exEmployer.setUsername(employerUpdated.getUsername());
				exEmployer.setEmail(employerUpdated.getEmail());
			}
		}*/
		//burda önce child attributeleri bulup, update et, sonra parent attributeları
		Job updatedJob = jobDao.getReferenceById(jobAdvertisementUpdated.getJob().getJobId());
		
		if (updatedJob != null)		
			exJobAdvertisement.setJob(updatedJob);
		else return new ErrorResult("JobAdvertisement job title is invalid!");
		
		City updatedCity = cityDao.getReferenceById(jobAdvertisementUpdated.getCity().getId());
		if (updatedCity != null)		
			exJobAdvertisement.setCity(updatedCity);
		else return new ErrorResult("JobAdvertisement city is invalid!");
		
		WorkingTime updatedWorkingTime = workingTimeDao.getReferenceById(jobAdvertisementUpdated.getWorkingTime().getId());
		if (updatedWorkingTime != null)		
			exJobAdvertisement.setWorkingTime(updatedWorkingTime);
		else return new ErrorResult("JobAdvertisement workingTime is invalid!");
		
		WorkingType updatedWorkingType = workingTypeDao.getReferenceById(jobAdvertisementUpdated.getWorkingType().getId());
		if (updatedWorkingType != null)		
			exJobAdvertisement.setWorkingType(updatedWorkingType);
		else return new ErrorResult("JobAdvertisement workingType is invalid!");
		
	    /*exJobAdvertisement.setCity(jobAdvertisementUpdated.getCity());
		exJobAdvertisement.setJob(jobAdvertisementUpdated.getJob());
		exJobAdvertisement.setWorkingType(jobAdvertisementUpdated.getWorkingType());
		exJobAdvertisement.setWorkingTime(jobAdvertisementUpdated.getWorkingTime());*/
		exJobAdvertisement.setMinSalary(jobAdvertisementUpdated.getMinSalary());
		exJobAdvertisement.setMaxSalary(jobAdvertisementUpdated.getMaxSalary());
		exJobAdvertisement.setOpenPositionAmount(jobAdvertisementUpdated.getOpenPositionAmount());
		exJobAdvertisement.setLastApplicationDate(jobAdvertisementUpdated.getLastApplicationDate());
		exJobAdvertisement.setDescription(jobAdvertisementUpdated.getDescription());
		jobAdvertisementDao.save(exJobAdvertisement);
		
		return new SuccessResult("JobAdvertisement updated succesfully");
	}

}
