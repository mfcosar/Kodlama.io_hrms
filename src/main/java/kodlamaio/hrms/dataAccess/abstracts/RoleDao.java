package kodlamaio.hrms.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kodlamaio.hrms.core.entities.ERole;
import kodlamaio.hrms.core.entities.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(ERole name);
}
