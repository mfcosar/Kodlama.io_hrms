package kodlamaio.hrms.entities.concretes.verifications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="employee_confirms_employers")

public class EmployeeConfirmEmployer extends EmployeeConfirm{
	
	@Column(name="employer_id")
	private int employerId;

	public EmployeeConfirmEmployer() {
		super();
	}

	public EmployeeConfirmEmployer(int employerId) {
		super();
		this.employerId = employerId;
	}

}
