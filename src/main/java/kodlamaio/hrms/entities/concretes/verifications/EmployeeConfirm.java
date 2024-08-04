package kodlamaio.hrms.entities.concretes.verifications;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED) //base tablolara bunu ekledik
@Table(name="employee_confirms")
public class EmployeeConfirm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@Column(name="is_confirmed")
	private boolean isConfirmed;
	
	@Column(name="confirm_date")
	private Date confirmDate;

	public EmployeeConfirm() {
		super();
	}

	public EmployeeConfirm(int id, int employeeId, boolean isConfirmed, Date confirmDate) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.isConfirmed = isConfirmed;
		this.confirmDate = confirmDate;
	}

}
