package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.CoverLetter;
import kodlamaio.hrms.entities.dtos.cv.CoverLetterDto;

public interface CoverLetterDao extends JpaRepository<CoverLetter, Integer>{
	
	//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.CoverLetterDto(cl.coverLetterDetails ) "
			+ "FROM CoverLetter cl JOIN cl.candidate c WHERE c.id = :candidateId ")
	List<CoverLetterDto> findCoverLetterDtosByCandidateId(int candidateId);
	
	//List<CoverLetter> findByCandidateId(int candidateId);

}
