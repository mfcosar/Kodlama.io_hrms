package kodlamaio.hrms.entities.concretes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "employers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "jobAdvertisements"})
public class Employer extends User{
	
	@NotBlank(message= "Şirket ismini giriniz!")
	@Column(name="company_name")
	private String companyName;
	
	@NotBlank(message= "Şirketinizin web adresini giriniz!")
	@Column(name="web_address")
	private String webAddress;
	
	
	@NotBlank(message= "Şirketinizin telefon numarasını giriniz!")
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="employee_confirmed") //admin confirmation gerekli
	private Boolean employeeConfirmed; 
	
	@OneToMany(mappedBy = "employer")
	private List<JobAdvertisement> jobAdvertisements;

	
	
	public Employer(String username, String email, String password,
			@NotBlank(message = "Please enter your compnay name!") String companyName,
			@NotBlank(message = "Please enter your web address!") String webAddress,
			@NotBlank(message = "Please enter your phone number!") String phoneNumber) {
		super(username, email, password);
		this.companyName= companyName;
		this.webAddress= webAddress;
		this.phoneNumber= phoneNumber;
		this.employeeConfirmed = false;
	}
	

}
