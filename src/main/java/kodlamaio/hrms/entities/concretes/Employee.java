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
public class Employee extends User{ //Burasi Admin, HRMS Employee olduğu için Employee dendi
	
	@NotBlank(message= "Enter your name!")
	@Column(name="first_name")
	private String firstName;
	
	
	@NotBlank(message= "Enter your last name!")
	@Column(name="last_name")
	private String lastName;

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
