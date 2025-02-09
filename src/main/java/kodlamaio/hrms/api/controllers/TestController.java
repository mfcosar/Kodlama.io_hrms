package kodlamaio.hrms.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")


public class TestController {
	
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EMPLOYER') or hasRole('ROLE_ADMIN')")
  public String userAccess() {
    return "User Content.";
  }
  
  @GetMapping("/candidate")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public String candidateAccess() {
    return "Candidate role is authenticated and Candidate Board is retrieved from backend.";
  }  
  
  @GetMapping("/employer")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public String employerAccess() {
    return "Employer Board";
  }
 
  @GetMapping("/employee")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  public String employeeAccess() {
    return "Employee Board";
  }
  
  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String adminAccess() {
    return "Admin Board";
  }


}
