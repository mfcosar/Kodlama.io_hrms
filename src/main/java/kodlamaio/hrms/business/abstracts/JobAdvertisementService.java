package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
//import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;

public interface JobAdvertisementService {
	
	DataResult<List<JobAdvertisement>> getAll();
	Result add(JobAdvertisement jobAdvertisement);
	DataResult<JobAdvertisement> deactiveJobAdvertisement(int jobAdvertisementId);
	DataResult<List<JobAdvertisement>> getUnconfirmedJobAdvertisements();
	DataResult<JobAdvertisement> confirmJobAdvertisementById(int jobAdvertisementId);
	
	//dtos
	DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosActive();
	DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosActiveOrderByPublicationDateAsc();
	DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerIdAndActive(int employerId);
	DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerId(int employerId);
	DataResult<List<JobAdvertisementDto>> findJobAdvertisementDtosByEmployerCompanyNameAndActive(String companyName);
	DataResult<List<JobAdvertisementDto>> getAllJobAdvertisementDtos();
	
	DataResult<JobAdvertisement> findJobAdvertisementById(int id);
	DataResult<List<JobAdvertisement>> findJobAdvertisementsByEmployerId(int employerId);
	Result update(JobAdvertisement jobAdvertisement);
	//DataResult<List<JobAdvertisement>> findByIsActiveTrue();
	//DataResult<List<JobAdvertisement>> findByIsActiveTrueOrderByPublicationDateAsc(); 
	//DataResult<List<JobAdvertisement>> findByEmployer_IdAndIsActiveTrue(int employerId);
	//DataResult<List<JobAdvertisement>> findByEmployer_CompanyNameAndIsActiveTrue(String companyName);
}
