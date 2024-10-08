package kodlamaio.hrms.dataAccess.abstracts.verifications;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;

public interface CandidateEmailVerificationDao extends JpaRepository<CandidateEmailVerification, Integer> {

}
