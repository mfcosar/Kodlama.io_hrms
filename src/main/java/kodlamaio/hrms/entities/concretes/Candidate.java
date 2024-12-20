package kodlamaio.hrms.entities.concretes;

//import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import kodlamaio.hrms.entities.concretes.cv.CoverLetter;
import kodlamaio.hrms.entities.concretes.cv.EducationInformation;
import kodlamaio.hrms.entities.concretes.cv.Image;
import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;
import kodlamaio.hrms.entities.concretes.cv.TechnologyAbility;
import kodlamaio.hrms.entities.concretes.cv.WebAddress;
import kodlamaio.hrms.entities.concretes.cv.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="candidates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "educationInformations", "workExperiences","languageInformations","webAddress", 
						"technologyAbilities", "coverLetters", "images"})
public class Candidate extends User{
	
	@NotBlank(message= "İsminizi giriniz!")
	@Column(name="first_name")
	private String firstName;
	
	
	@NotBlank(message= "Soyadınızı giriniz!")
	@Column(name="last_name")
	private String lastName;
	
	
	@NotBlank(message= "TC Kimlik Numaranızı giriniz!")
	@Column(name="tc_identity_number")
	private String tcIdentityNumber;
	
	
	@NotBlank(message= "Doğum yılınızı giriniz!")
	@Column(name="birth_year")
	private int birthYear;
	
	@OneToMany(mappedBy = "candidate")
	private List<EducationInformation> educationInformations;
	
	@OneToMany(mappedBy = "candidate")
	private List<WorkExperience> workExperiences;
	
	@OneToMany(mappedBy = "candidate")
	private List<LanguageInformation> languageInformations;
	
	@OneToOne(mappedBy="candidate")
	private WebAddress webAddress;
	
	@OneToMany(mappedBy = "candidate")
	private List<TechnologyAbility> technologyAbilities;
	
	@OneToMany(mappedBy = "candidate")
	private List<CoverLetter> coverLetters;

	@OneToMany(mappedBy = "candidate")
	private List<Image> images;

	public Candidate(String username, String email, String password,
			@NotBlank(message = "İsminizi giriniz!") String firstName,
			@NotBlank(message = "Soyadınızı giriniz!") String lastName,
			@NotBlank(message = "TC Kimlik Numaranızı giriniz!") String tcIdentityNumber,
			int birthYear) {
		super(username, email, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.tcIdentityNumber = tcIdentityNumber;
		this.birthYear = birthYear;
	}
	
	/*public Candidate(String username, String email, String password, boolean isVerified, LocalDateTime verificationExpiry,
			@NotBlank(message = "İsminizi giriniz!") String firstName,
			@NotBlank(message = "Soyadınızı giriniz!") String lastName,
			@NotBlank(message = "TC Kimlik Numaranızı giriniz!") String tcIdentityNumber,
			@NotBlank(message = "Doğum yılınızı giriniz!") int birthYear) {
		super(username, email, password, isVerified,verificationExpiry);
		this.firstName = firstName;
		this.lastName = lastName;
		this.tcIdentityNumber = tcIdentityNumber;
		this.birthYear = birthYear;
	}*/
}
