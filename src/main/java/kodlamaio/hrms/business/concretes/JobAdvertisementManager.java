package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;

@Service
public class JobAdvertisementManager implements JobAdvertisementService{
	
	private JobAdvertisementDao jobAdvertisementDao;

	@Autowired
	public JobAdvertisementManager(JobAdvertisementDao jobAdvertisementDao) {
		super();
		this.jobAdvertisementDao = jobAdvertisementDao;
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

	

}
