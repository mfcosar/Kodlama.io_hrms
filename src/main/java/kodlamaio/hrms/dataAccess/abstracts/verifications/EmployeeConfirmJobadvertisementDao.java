package kodlamaio.hrms.dataAccess.abstracts.verifications;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmJobadvertisement;

public interface EmployeeConfirmJobadvertisementDao extends JpaRepository<EmployeeConfirmJobadvertisement, Integer>{

	Optional<EmployeeConfirmJobadvertisement> findByJobadvertisementId(int jobadvertisementId);
	boolean deleteByJobadvertisementId(int jobadvertisementId);
}

