package kodlamaio.hrms.dataAccess.abstracts.verifications;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.verifications.EmployeeEmailVerification;

public interface EmployeeEmailVerificationDao extends JpaRepository<EmployeeEmailVerification, Integer>{
	Optional<EmployeeEmailVerification> findByCode(String code);
	Optional<EmployeeEmailVerification> findByEmployeeId(int employeeId);

}
