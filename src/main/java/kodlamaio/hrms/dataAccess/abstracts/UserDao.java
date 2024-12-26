package kodlamaio.hrms.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Integer> {
	
	  Optional<User> findByUsername(String username);
	  Optional<User> findById(int id);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);

}
