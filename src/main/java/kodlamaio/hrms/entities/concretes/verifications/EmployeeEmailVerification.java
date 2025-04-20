package kodlamaio.hrms.entities.concretes.verifications;

import java.time.LocalDateTime;

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
@Table(name="verification_code_employees")
public class EmployeeEmailVerification extends EmailVerification{
	
	@Column(name="employee_id")
	private int employeeId;

	public EmployeeEmailVerification(String code, Boolean verified, LocalDateTime verificationExpiry, int employeeId) {
		super(code, verified, verificationExpiry);
		this.employeeId= employeeId;
		
	}
}
