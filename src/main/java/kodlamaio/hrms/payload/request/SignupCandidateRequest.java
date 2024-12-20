package kodlamaio.hrms.payload.request;

//import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignupCandidateRequest {
	  @NotBlank
	  @Size(min = 3, max = 50)
	  private String username;

	  @NotBlank
	  @Size(max = 50)
	  @Email
	  private String email;

	  private Set<String> role;

	  @NotBlank
	  @Size(min = 6, max = 25)
	  private String password;
	  
	  @NotBlank(message= "İsminizi giriniz!")
	  private String firstName;
		
		
	  @NotBlank(message= "Soyadınızı giriniz!")
	  private String lastName;
		
		
	  @NotBlank(message= "TC Kimlik Numaranızı giriniz!")
	  private String tcIdentityNumber;
		
		
	  @NotNull(message= "Doğum yılınızı giriniz!")
	  private int birthYear; 
		
		//private boolean verified;private LocalDateTime verificationExpiry;	  

	  public String getUsername() {
	    return username;
	  }

	  public void setUsername(String username) {
	    this.username = username;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }

	  public Set<String> getRole() {
	    return this.role;
	  }

	  public void setRole(Set<String> role) {
	    this.role = role;
	  }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTcIdentityNumber() {
		return tcIdentityNumber;
	}

	public void setTcIdentityNumber(String tcIdentityNumber) {
		this.tcIdentityNumber = tcIdentityNumber;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	/*public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public LocalDateTime getVerificationExpiry() {
		return verificationExpiry;
	}

	public void setVerificationExpiry(LocalDateTime verificationExpiry) {
		this.verificationExpiry = verificationExpiry;
	}*/
	}