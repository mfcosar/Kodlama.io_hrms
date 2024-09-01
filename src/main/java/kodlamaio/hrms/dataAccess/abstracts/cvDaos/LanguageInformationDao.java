package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;
import kodlamaio.hrms.entities.dtos.cv.LanguageInformationDto;

public interface LanguageInformationDao extends JpaRepository<LanguageInformation, Integer>{
	
	//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.LanguageInformationDto(li.foreignLanguageName, li.foreignLanguageLevel ) "
			+ "FROM LanguageInformation li JOIN li.candidate c WHERE c.id = :candidateId ORDER BY li.foreignLanguageName ASC")
	List<LanguageInformationDto> findLanguageInformationDtoByCandidateId(int candidateId);
	
	//	List<LanguageInformation> findByCandidateId(int candidateId);

}
