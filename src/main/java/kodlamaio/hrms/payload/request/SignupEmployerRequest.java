package kodlamaio.hrms.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupEmployerRequest {
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
	  
      @NotBlank(message= "Şirket ismini giriniz!")
	  private String companyName;
		
	  @NotBlank(message= "Şirketinizin web adresini giriniz!")
      private String webAddress;
		
		
	  @NotBlank(message= "Şirketinizin telefon numarasını giriniz!")
      private String phoneNumber;


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


	public Set<String> getRole() {
		return role;
	}


	public void setRole(Set<String> role) {
		this.role = role;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getWebAddress() {
		return webAddress;
	}


	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	  

}
