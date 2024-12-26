package kodlamaio.hrms.dataAccess.abstracts.verifications;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;

public interface CandidateEmailVerificationDao extends JpaRepository<CandidateEmailVerification, Integer> {
	
	Optional<CandidateEmailVerification> findByCode(String code);
	Optional<CandidateEmailVerification> findByCandidateId(int candidateId);

}
