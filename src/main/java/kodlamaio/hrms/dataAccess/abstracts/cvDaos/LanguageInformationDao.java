package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;

public interface LanguageInformationDao extends JpaRepository<LanguageInformation, Integer>{
	
	List<LanguageInformation> findByCandidateId(int candidateId);
	

}
