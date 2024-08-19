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
		return new SuccessResult("İş ilanı eklendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> findByIsActiveTrue() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findByIsActiveTrue(), "Tüm aktif iş ilanları listelendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> findByIsActiveTrueOrderByPublicationDateAsc() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findByIsActiveTrueOrderByPublicationDateAsc(), "Tüm aktif iş ilanları yayınlanma tarihine göre dizilip listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisement>> findByEmployer_IdAndIsActiveTrue(int employerId) {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findByEmployer_IdAndIsActiveTrue(employerId), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public DataResult<List<JobAdvertisement>> findByEmployer_CompanyNameAndIsActiveTrue(String companyName) {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findByEmployer_CompanyNameAndIsActiveTrue(companyName), "Firmaya ait tüm aktif iş ilanları listelendi.");
	}

	@Override
	public DataResult<JobAdvertisement> deactiveJobAdvertisement(int jobAdvertisementId) {
		
		JobAdvertisement ja = this.jobAdvertisementDao.findById(jobAdvertisementId);
		ja.setActive(false);
		this.jobAdvertisementDao.save(ja); //Db'nin save etmesi gerek
		return new SuccessDataResult<JobAdvertisement>(this.jobAdvertisementDao.findById(jobAdvertisementId), jobAdvertisementId+ " : Nolu iş ilanı pasifleştirildi");
	}

}
