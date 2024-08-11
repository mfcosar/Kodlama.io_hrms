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
@Table(name = "employees")
public class Employee extends User{
	
	@NotBlank(message= "İsminizi giriniz!")
	@Column(name="first_name")
	private String firstName;
	
	
	@NotBlank(message= "Soyadınızı giriniz!")
	@Column(name="last_name")
	private String lastName;

}
