package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;
import kodlamaio.hrms.entities.dtos.cv.TechnologyAbilityDto;

public interface TechnologyAbilityDao extends JpaRepository<TechnologyAbility, Integer>{

	//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.TechnologyAbilityDto(ta.technologyName) "
			+ "FROM TechnologyAbility ta JOIN ta.candidate c WHERE c.id = :candidateId ORDER BY ta.technologyName ASC")
	List<TechnologyAbilityDto> findTechnologyAbilityDtosByCandidateId(int candidateId);
	
	//List<TechnologyAbility> findByCandidateId(int candidateId);
}
