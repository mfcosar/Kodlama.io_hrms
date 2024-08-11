package kodlamaio.hrms.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Employer extends User{
	
	@NotBlank(message= "Şirket ismini giriniz!")
	@Column(name="company_name")
	private String companyName;
	
	@NotBlank(message= "Şirketinizin web adresini giriniz!")
	@Column(name="web_address")
	private String webAddress;
	

	
	

}
