package kodlamaio.hrms.entities.concretes.verifications;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kodlamaio.hrms.entities.concretes.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@Table(name="employee_confirms_jobadvertisements")

public class EmployeeConfirmJobadvertisement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;
	
	/*@Column(name="employee_id")
	private Integer employeeId;*/

	@ManyToOne()
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@Column(name="jobadvertisement_id")
	private int jobadvertisementId;

	@Column(name="confirm_date")
	private LocalDateTime confirmDate;

	@Column(name="is_confirmed")
	private Boolean isConfirmed;
	
	public EmployeeConfirmJobadvertisement(int jobadvertisementId, Boolean isConfirmed) {
		super();
		this.jobadvertisementId = jobadvertisementId;
		this.isConfirmed = isConfirmed;
	}	
	


}
