package kodlamaio.hrms.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kodlamaio.hrms.core.entities.RefreshToken;
import kodlamaio.hrms.dataAccess.abstracts.RefreshTokenDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.security.jwt.exceptions.TokenRefreshException;

@Service
public class RefreshTokenService {
  @Value("${bezkoder.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenDao refreshTokenRepository;

  @Autowired
  private UserDao userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(int userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(int userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}