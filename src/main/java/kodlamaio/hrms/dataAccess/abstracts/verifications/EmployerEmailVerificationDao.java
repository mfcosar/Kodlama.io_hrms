package kodlamaio.hrms.dataAccess.abstracts.verifications;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.verifications.EmployerEmailVerification;

public interface EmployerEmailVerificationDao extends JpaRepository<EmployerEmailVerification, Integer>{
	Optional<EmployerEmailVerification> findByCode(String code);
	Optional<EmployerEmailVerification> findByEmployerId(int employerId);

}
