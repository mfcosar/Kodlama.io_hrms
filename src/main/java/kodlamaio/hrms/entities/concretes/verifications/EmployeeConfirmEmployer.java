package kodlamaio.hrms.entities.concretes.verifications;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@Table(name="employee_confirms_employers")

public class EmployeeConfirmEmployer {//extends EmployeeConfirm{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;
	
	@Column(name="employee_id")
	private Integer employeeId;
	
	@Column(name="employer_id")
	private int employerId;
	
	@Column(name="is_confirmed")
	private Boolean isConfirmed;
	
	@Column(name="confirm_date")
	private LocalDateTime confirmDate;

	public EmployeeConfirmEmployer(int employerId, Boolean isConfirmed) {
		super();
		this.employerId = employerId;
		this.isConfirmed = isConfirmed;
	}	
	


}
