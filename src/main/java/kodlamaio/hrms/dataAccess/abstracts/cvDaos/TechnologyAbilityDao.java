package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;

public interface TechnologyAbilityDao extends JpaRepository<TechnologyAbility, Integer>{
	List<TechnologyAbility> findByCandidateId(int candidateId);
}
