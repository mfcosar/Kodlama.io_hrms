package kodlamaio.hrms.entities.concretes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmEmployer;
import kodlamaio.hrms.entities.concretes.verifications.EmployeeConfirmJobadvertisement;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "employeeConfirmJobadvertisement", "employeeConfirmEmployer"})//yazmayınca warning exception
public class Employee extends User{ //Burasi Admin, HRMS Employee olduğu için Employee dendi
	
	@NotBlank(message= "Enter your name!")
	@Column(name="first_name")
	private String firstName;
	
	
	@NotBlank(message= "Enter your last name!")
	@Column(name="last_name")
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<EmployeeConfirmEmployer> employeeConfirmEmployers;
	
	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<EmployeeConfirmJobadvertisement> employeeConfirmJobadvertisements;   

	public Employee(int id, String username, String email, String password, String firstName, String lastName) {
		super(username, email, password);
		super.setId(id);
		/*super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);*/
		this.firstName= firstName;
		this.lastName = lastName;
	}

	public Employee(String username, String email, String password, String firstName, String lastName) {
		super(username, email, password);
		/*super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);*/
		this.firstName= firstName;
		this.lastName = lastName;
	}	

}
