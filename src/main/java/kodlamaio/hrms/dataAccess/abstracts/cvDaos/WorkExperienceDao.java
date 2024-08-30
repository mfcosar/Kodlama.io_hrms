package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cv.WorkExperience;

public interface WorkExperienceDao extends JpaRepository<WorkExperience, Integer>{
	
	List<WorkExperience> findByCandidateIdOrderByEndDateDesc(int candidateId);
	List<WorkExperience> findByCandidateIdAndIsContinuedTrue(int candidateId);

}
