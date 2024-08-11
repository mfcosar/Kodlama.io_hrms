package kodlamaio.hrms.entities.concretes.verifications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="employee_confirms_employers")

public class EmployeeConfirmEmployer extends EmployeeConfirm{
	
	@Column(name="employer_id")
	private int employerId;

}
