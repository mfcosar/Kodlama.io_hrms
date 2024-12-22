package kodlamaio.hrms.entities.concretes.verifications;


import java.time.LocalDateTime;

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
@Table(name="verification_code_candidates")

public class CandidateEmailVerification extends EmailVerification{
	
	@NotBlank
	@Column(name="candidate_id")
	private int candidateId;
	
	public CandidateEmailVerification(String code, Boolean verified, LocalDateTime verificationExpiry, int candidateId) {
		super(code, verified, verificationExpiry);
		this.candidateId= candidateId;
		
	}
	
	

}
