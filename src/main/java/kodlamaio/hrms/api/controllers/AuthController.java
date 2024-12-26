package kodlamaio.hrms.api.controllers;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.entities.ERole;
import kodlamaio.hrms.core.entities.RefreshToken;
import kodlamaio.hrms.core.entities.Role;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.RoleDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployerEmailVerificationDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;
import kodlamaio.hrms.payload.request.LoginRequest;
import kodlamaio.hrms.payload.request.SignupCandidateRequest;
import kodlamaio.hrms.payload.request.SignupRequest;
import kodlamaio.hrms.payload.request.TokenRefreshRequest;
import kodlamaio.hrms.payload.response.JwtResponse;
import kodlamaio.hrms.payload.response.MessageResponse;
import kodlamaio.hrms.payload.response.TokenRefreshResponse;
import kodlamaio.hrms.security.jwt.JwtUtils;
import kodlamaio.hrms.security.jwt.exceptions.TokenRefreshException;
import kodlamaio.hrms.security.services.RefreshTokenService;
import kodlamaio.hrms.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserDao userRepository;
  
  @Autowired
  CandidateDao candidateRepository;
  
  @Autowired
  CandidateService candidateService;

  @Autowired
  RoleDao roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  
  @Autowired
  RefreshTokenService refreshTokenService;
  
  @Autowired
  CandidateEmailVerificationDao candidateEmailVerificationDao;
  
  @Autowired
  EmployerEmailVerificationDao employerEmailVerificationDao;
  
  @Autowired
  EmailVerificationDao emailVerificationDao;
  

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	  
	  //verification check yapılır, olumsuz ise user bütün db'den silinir
	  String username= loginRequest.getUsername();
	  Optional<User> optUser;
	  
	  optUser = userRepository.findByUsername(username);
	  if (optUser.isPresent()) {
		  User user = optUser.get();
		  if (!user.getVerified()) {
			  //check if verification link has expired
			  
			  //bütün verifications'u bir tek emailverificationsa da topla çok dallandırma..
			  
			  Optional<CandidateEmailVerification> optCandidateEmailVerification= candidateEmailVerificationDao.findByCandidateId(user.getId());
			  if (optCandidateEmailVerification.isPresent()) {
				  CandidateEmailVerification candidateEmailVerification= optCandidateEmailVerification.get();
				  if (candidateEmailVerification.getVerificationExpiry().isBefore(LocalDateTime.now())) {
					  //expired: delete user from all db tables
					  //userRolesDao.deleteById(user.getId());
					  user.setRoles(null); //burda UserRolesDao lazım mı??
					  //find verification record & delete
					  int verificationId = candidateEmailVerification.getId();
					  int candidateId = candidateEmailVerification.getCandidateId();
					  candidateEmailVerificationDao.deleteById(verificationId);
					  emailVerificationDao.deleteById(verificationId);
					  candidateRepository.deleteById(candidateId);
					  userRepository.deleteById(user.getId());
					  //silinecekler var candidateDao/employerDao' dan sil.. 
					  
					  return ResponseEntity
					          .badRequest()
					          .body(new MessageResponse("Verification link has expired. Please sign up again!"));
				  }
				  else return ResponseEntity
				          .badRequest()
				          .body(new MessageResponse("User account is not verified yet! Please check your email box to verify your HRMS account."));
			  }

		  }
	  }else {
		  return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Invalid username!"));
	  }
	  
	//user verified ise aşağıdaki kod ile authentication yapılır	 
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(), roles));
  }
  
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService :: verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }
  

  @PostMapping("/signup/user")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "candidate":
            Role candidateRole = roleRepository.findByName(ERole.ROLE_CANDIDATE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(candidateRole);

            break;               
        case "employer":
          Role employerRole = roleRepository.findByName(ERole.ROLE_EMPLOYER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(employerRole);

          break;
        case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;
       
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  @PostMapping("/signup/candidate")
  public ResponseEntity<?> registerCandidate(@Valid @RequestBody SignupCandidateRequest signUpCandidateRequest) {
	  
    if (userRepository.existsByUsername(signUpCandidateRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpCandidateRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    
    if (candidateRepository.existsByTcIdentityNumber(signUpCandidateRequest.getTcIdentityNumber())){
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: TC identity number is already in use!"));
      }    

    // Create new candidate's account
    Candidate newCandidate = new Candidate(signUpCandidateRequest.getUsername(), signUpCandidateRequest.getEmail(),
               encoder.encode(signUpCandidateRequest.getPassword()), 
               signUpCandidateRequest.getFirstName(), signUpCandidateRequest.getLastName(),
               signUpCandidateRequest.getTcIdentityNumber(), signUpCandidateRequest.getBirthYear());
    //signUpCandidateRequest.isVerified(), signUpCandidateRequest.getVerificationExpiry(),

    Set<String> strRoles = signUpCandidateRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role candidateRole = roleRepository.findByName(ERole.ROLE_CANDIDATE)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(candidateRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "candidate":
            Role candidateRole = roleRepository.findByName(ERole.ROLE_CANDIDATE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(candidateRole);

            break;               
        case "employer":
          Role employerRole = roleRepository.findByName(ERole.ROLE_EMPLOYER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(employerRole);

          break;
        case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;
       
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    newCandidate.setRoles(roles);
    candidateService.add(newCandidate);

    return ResponseEntity.ok(new MessageResponse("Candidate registered successfully!"));
  }
  
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() { 
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}