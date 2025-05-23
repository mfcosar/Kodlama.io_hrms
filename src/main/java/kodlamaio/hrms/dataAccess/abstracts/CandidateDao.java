package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Candidate;

public interface CandidateDao  extends JpaRepository<Candidate, Integer>{
	
	Candidate findCandidateById(int candidateId);
	boolean existsByTcIdentityNumber(String tcIdentityNumber); 
	boolean existsByUsername(String username);
}
