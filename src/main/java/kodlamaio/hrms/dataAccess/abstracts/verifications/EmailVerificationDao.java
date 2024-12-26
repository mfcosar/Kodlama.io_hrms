package kodlamaio.hrms.dataAccess.abstracts.verifications;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.verifications.EmailVerification;

public interface EmailVerificationDao  extends JpaRepository<EmailVerification, Integer> {
	Optional<EmailVerificationDao> findByCode(String code);
	Optional<EmailVerificationDao> findById(int id);
}
