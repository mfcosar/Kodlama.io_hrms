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
@Table(name="verification_code_employers")
public class EmployerEmailVerification extends EmailVerification{
	
	@Column(name="employer_id")
	private int employerId;

	public EmployerEmailVerification(String code, Boolean verified, LocalDateTime verificationExpiry, int employerId) {
		super(code, verified, verificationExpiry);
		this.employerId= employerId;
		
	}
}
