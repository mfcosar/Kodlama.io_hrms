package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cv.EducationInformation;

public interface EducationInformationDao extends JpaRepository<EducationInformation, Integer>{
	
	List<EducationInformation> findByCandidateIdOrderByEndDateDesc(int candidateId);
	List<EducationInformation> findByCandidateIdAndIsGraduateFalse(int candidateId);

}
