package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;


public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement, Integer>{
	
	JobAdvertisement findById(int jobAdvertisementId);
	List<JobAdvertisement> findByIsConfirmedFalseOrderById();
	
	//dto operations
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE ja.isActive = true")
	List<JobAdvertisementDto> findJobAdvertisementDtosActive();
	
	/*@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE ja.id = :id")
	List<JobAdvertisementDto> findJobAdvertisementDtoById(int id); */
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE ja.isActive = true ORDER BY ja.publicationDate ASC")
	List<JobAdvertisementDto> findJobAdvertisementDtosActiveOrderByPublicationDateAsc();
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE ja.isActive = true AND e.id = :employerId ")
	List<JobAdvertisementDto> findJobAdvertisementDtosByEmployerIdAndActive(int employerId);
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE ja.isActive = true AND e.companyName = :companyName ")
	List<JobAdvertisementDto> findJobAdvertisementDtosByEmployerCompanyNameAndActive(String companyName);
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e ORDER BY ja.publicationDate ASC ")
	List<JobAdvertisementDto> getAllJobAdvertisementDtos();
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(e.companyName, j.jobTitle, ja.openPositionAmount, ja.publicationDate, ja.lastApplicationDate  ) "
			+ "FROM JobAdvertisement ja JOIN ja.job j JOIN ja.employer e WHERE e.id = :employerId ")
	List<JobAdvertisementDto> findJobAdvertisementDtosByEmployerId(int employerId);	
	
	List<JobAdvertisement> findJobAdvertisementsByEmployerId(int employerId);
	
	/* List<JobAdvertisement> findByIsActiveTrue();
	List<JobAdvertisement> findByIsActiveTrueOrderByPublicationDateAsc(); 
	List<JobAdvertisement> findByEmployer_IdAndIsActiveTrue(int employerId);
	List<JobAdvertisement> findByEmployer_CompanyNameAndIsActiveTrue(String companyName);*/
}
