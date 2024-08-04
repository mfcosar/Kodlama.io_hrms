package kodlamaio.hrms.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="candidates")

public class Candidate extends User{
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="tc_identity_number")
	private String tcIdentityNumber;
	
	@Column(name="birth_year")
	private int birthYear;

	public Candidate() {
		super();
	}
	
	public Candidate(String firstName, String lastName, String tcIdentityNumber, int birthYear) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.tcIdentityNumber = tcIdentityNumber;
		this.birthYear = birthYear;
	}


	

}
