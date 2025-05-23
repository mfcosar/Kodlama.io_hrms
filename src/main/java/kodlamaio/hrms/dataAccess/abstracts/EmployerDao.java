package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Employer;

public interface EmployerDao extends JpaRepository<Employer, Integer>{
	Employer findById(int employerId);

	boolean existsByUsername(String username);
	
	List<Employer> findByEmployeeConfirmedFalseOrderById();

}
