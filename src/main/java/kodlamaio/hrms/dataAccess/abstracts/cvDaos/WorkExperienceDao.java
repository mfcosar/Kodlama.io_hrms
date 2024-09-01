package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.WorkExperience;
import kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto;

public interface WorkExperienceDao extends JpaRepository<WorkExperience, Integer>{
	
	//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto(we.companyName, we.positionName, we.beginDate, we.endDate, we.isContinued ) "
			+ "FROM WorkExperience we JOIN we.candidate c WHERE c.id = :candidateId ORDER BY we.endDate DESC")
	List<WorkExperienceDto> findWorkExperienceDtoByCandidateIdOrderByEndDateDesc(int candidateId);
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.WorkExperienceDto(we.companyName, we.positionName, we.beginDate, we.endDate, we.isContinued ) "
			+ "FROM WorkExperience we JOIN we.candidate c WHERE c.id = :candidateId AND we.isContinued = true")
	List<WorkExperienceDto> findWorkExperienceDtoByCandidateIdAndIsContinuedTrue(int candidateId);
	
	/*List<WorkExperience> findByCandidateIdOrderByEndDateDesc(int candidateId);
	List<WorkExperience> findByCandidateIdAndIsContinuedTrue(int candidateId);*/

}
