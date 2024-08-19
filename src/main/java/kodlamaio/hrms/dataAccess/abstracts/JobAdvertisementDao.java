package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;


public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement, Integer>{
	
	@Query("From JobAdvertisement where isActive = true ")     // and category.categoryId = :categoryId")
	List<JobAdvertisement> findByIsActiveTrue();
	List<JobAdvertisement> findByIsActiveTrueOrderByPublicationDateAsc();   
	List<JobAdvertisement> findByEmployer_IdAndIsActiveTrue(int employerId);
	List<JobAdvertisement> findByEmployer_CompanyNameAndIsActiveTrue(String companyName);
	JobAdvertisement findById(int jobAdvertisementId);
}