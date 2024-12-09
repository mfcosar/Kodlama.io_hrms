package kodlamaio.hrms.dataAccess.abstracts;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import kodlamaio.hrms.core.entities.RefreshToken;
import kodlamaio.hrms.entities.concretes.User;


@Repository
public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByUser(User user);
}
