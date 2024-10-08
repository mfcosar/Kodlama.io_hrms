package kodlamaio.hrms.dataAccess.abstracts.verifications;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.verifications.EmployerEmailVerification;

public interface EmployerEmailVerificationDao extends JpaRepository<EmployerEmailVerification, Integer>{

}
