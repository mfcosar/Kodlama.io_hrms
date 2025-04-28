package kodlamaio.hrms.api.controllers;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.EmployeeService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.entities.ERole;
import kodlamaio.hrms.core.entities.RefreshToken;
import kodlamaio.hrms.core.entities.Role;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.EmployeeDao;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.RoleDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.CandidateEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeConfirmEmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployeeEmailVerificationDao;
import kodlamaio.hrms.dataAccess.abstracts.verifications.EmployerEmailVerificationDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.User;
import kodlamaio.hrms.entities.concretes.verifications.CandidateEmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeEmailVerification;
import kodlamaio.hrms.entities.concretes.verifications.EmployerEmailVerification;
import kodlamaio.hrms.payload.request.LoginRequest;
import kodlamaio.hrms.payload.request.SignupCandidateRequest;
import kodlamaio.hrms.payload.request.SignupEmployeeRequest;
import kodlamaio.hrms.payload.request.SignupEmployerRequest;
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
  EmployerDao employerRepository;

  @Autowired
  EmployeeDao employeeRepository;
  
  @Autowired
  CandidateService candidateService;
  
  @Autowired
  EmployerService employerService;
  
  @Autowired
  EmployeeService employeeService;
  
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
  EmployeeEmailVerificationDao employeeEmailVerificationDao;
  
  @Autowired
  EmployerEmailVerificationDao employerEmailVerificationDao;
  
  @Autowired
  EmailVerificationDao emailVerificationDao;

  @Autowired
  EmployeeConfirmEmployerDao employeeConfirmEmployerDao;  

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	  
	  //verification check yapılır, olumsuz ise user bütün db'den silinir
	  String username= loginRequest.getUsername();
	  Optional<User> optUser;
	  
	  optUser = userRepository.findByUsername(username);
	  if (optUser.isPresent()) {
		  User user = optUser.get();
		  
		  Optional<EmployeeConfirmEmployer> optEmployeeConfirmEmployer= employeeConfirmEmployerDao.findByEmployerId(user.getId());
		  Optional<CandidateEmailVerification> optCandidateEmailVerification= candidateEmailVerificationDao.findByCandidateId(user.getId());
		  Optional<EmployerEmailVerification> optEmployerEmailVerification= employerEmailVerificationDao.findByEmployerId(user.getId());
		  Optional<EmployeeEmailVerification> optEmployeeEmailVerification= employeeEmailVerificationDao.findByEmployeeId(user.getId());
		  
		  
		  if (!user.getVerified()) {
			  //check if verification link has expired
			  
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
			 
			  //if user is an employee, than email verification check			  
			  else if (optEmployeeEmailVerification.isPresent()) {
				  EmployeeEmailVerification employeeEmailVerification= optEmployeeEmailVerification.get();
				  if (employeeEmailVerification.getVerificationExpiry().isBefore(LocalDateTime.now())) {
					  //expired: delete user from all db tables
					  //userRolesDao.deleteById(user.getId());
					  user.setRoles(null); //burda UserRolesDao lazım mı??
					  //find verification record & delete
					  int verificationId = employeeEmailVerification.getId();
					  int employeeId = employeeEmailVerification.getEmployeeId();
					  employeeEmailVerificationDao.deleteById(verificationId);
					  emailVerificationDao.deleteById(verificationId);
					  employeeRepository.deleteById(employeeId);
					  userRepository.deleteById(user.getId());
					  //silinecekler var employeeDao' dan sil.. 
					  
					  return ResponseEntity
					          .badRequest()
					          .body(new MessageResponse("Verification link has expired. Please sign up again!"));
				  }
				  else return ResponseEntity
				          .badRequest()
				          .body(new MessageResponse("User account is not verified yet! Please check your email box to verify your HRMS account."));
			  }
			  
			  		  
			  //if user is an employer, than email verification check
			  else if (optEmployerEmailVerification.isPresent()) {  
				  EmployerEmailVerification employerEmailVerification= optEmployerEmailVerification.get();
				  if (employerEmailVerification.getVerificationExpiry().isBefore(LocalDateTime.now())) {
					  //expired: delete user from all db tables
					  user.setRoles(null); //burda UserRolesDao lazım mı??
					  //find verification record & delete
					  int verificationId = employerEmailVerification.getId();
					  int employerId = employerEmailVerification.getEmployerId();
					  employerEmailVerificationDao.deleteById(verificationId);
					  emailVerificationDao.deleteById(verificationId);
					  employeeConfirmEmployerDao.deleteByEmployerId(employerId);
					  employerRepository.deleteById(employerId);
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
			  
			  else return ResponseEntity
			          .badRequest()
			          .body(new MessageResponse("User account is not verified yet! Please check your email box to verify your HRMS account."));
			  
		  } //user is verified but if user is employer second employee confirmation must be checked
		  else if ( user.getVerified() && optEmployerEmailVerification.isPresent() && !optEmployeeConfirmEmployer.isPresent())
			  return ResponseEntity
			          .badRequest()
			          .body(new MessageResponse("Employer must be confirmed by HRMS personnel!"));
		  
		  else if ( user.getVerified() && optEmployeeConfirmEmployer.isPresent()) { //user.getVerified() &&
			  EmployeeConfirmEmployer employeeConfirmEmployer = optEmployeeConfirmEmployer.get();
			  if (!employeeConfirmEmployer.getIsConfirmed())
				  return ResponseEntity
				          .badRequest()
				          .body(new MessageResponse("Employer account has not been confirmed by HRMS personnel yet!"));
		 }
	  } // optUser is not present
	  else if (!optUser.isPresent()) {
		  System.out.print("This username cannot be found in db : "+username);
		  
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
    
    //System.out.println("loginRequest.getUsername(): "+loginRequest.getUsername() +"   roles: " + roles.getFirst());
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
        /*case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;*/
       
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
        /*case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;*/
       
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
  
  
  @PostMapping("/signup/employer")
  public ResponseEntity<?> registerEmployer(@Valid @RequestBody SignupEmployerRequest signupEmployerRequest) {
	  
    if (userRepository.existsByUsername(signupEmployerRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signupEmployerRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    
    //check domains
    String emailDomain = extractDomainFromEmail(signupEmployerRequest.getEmail()); 
    String webDomain = extractDomainFromWebAddress(signupEmployerRequest.getWebAddress()); 
    
    if (!emailDomain.equalsIgnoreCase(webDomain)) {
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Email and web adress domains do not match!"));
      }
   
    // Form new employer's account
    Employer newEmployer = new Employer(signupEmployerRequest.getUsername(), signupEmployerRequest.getEmail(),
    		encoder.encode(signupEmployerRequest.getPassword()), signupEmployerRequest.getCompanyName(), signupEmployerRequest.getWebAddress(), 
    		signupEmployerRequest.getPhoneNumber());
       
    Set<String> strRoles = signupEmployerRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role employerRole = roleRepository.findByName(ERole.ROLE_EMPLOYER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(employerRole);
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
        /*case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;*/
       
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    newEmployer.setRoles(roles);
    employerService.add(newEmployer); //manager'da eklenen employer için verification ve confirmation girdileri db'e ekleniyor

    return ResponseEntity.ok(new MessageResponse("Employer registered successfully!"));
  }
  
  private static String extractDomainFromEmail(String email) { 
	  int atIndex = email.indexOf('@'); 
	  return email.substring(atIndex + 1); 
	  } 
  
  private static String extractDomainFromWebAddress(String webAddress) { 
	  String domain = webAddress.replaceFirst("https?://(www\\.)?", ""); 
	  int slashIndex = domain.indexOf('/'); 
	  if (slashIndex != -1) { 
		  domain = domain.substring(0, slashIndex); } 
	  return domain; 
  }
  
  
  
  
  @PostMapping("/signup/employee")
  public ResponseEntity<?> registerEmployee(@Valid @RequestBody SignupEmployeeRequest signupEmployeeRequest) {
	  
    if (userRepository.existsByUsername(signupEmployeeRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signupEmployeeRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    
    
   // Form new employee's account
    Employee newEmployee = new Employee(signupEmployeeRequest.getUsername(), signupEmployeeRequest.getEmail(),
    		encoder.encode(signupEmployeeRequest.getPassword()), signupEmployeeRequest.getFirstName(), signupEmployeeRequest.getLastName());
   
    Set<String> strRoles = signupEmployeeRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role employeeRole = roleRepository.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(employeeRole);
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
        /*case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            break;*/
       
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    newEmployee.setRoles(roles);
    employeeService.add(newEmployee);

    return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
  }

  
  
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() { 
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}