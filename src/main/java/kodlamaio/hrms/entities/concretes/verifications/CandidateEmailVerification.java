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
@Table(name="verification_code_candidates")

public class CandidateEmailVerification extends EmailVerification{
	
	@Column(name="candidate_id")
	private int candidateId;

}
