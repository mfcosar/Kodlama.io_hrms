package kodlamaio.hrms.dataAccess.abstracts.verifications;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;

public interface EmployeeConfirmEmployerDao extends JpaRepository<EmployeeConfirmEmployer, Integer>{

	Optional<EmployeeConfirmEmployer> findByEmployerId(int employerId);
	boolean deleteByEmployerId(int employerId);
	
}
