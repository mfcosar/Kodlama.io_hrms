package kodlamaio.hrms.entities.concretes.verifications;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="verification_code_candidates")

public class CandidateEmailVerification extends EmailVerification{
	
	@Column(name="candidate_id")
	private int candidateId;

	public CandidateEmailVerification() {
		super();
	}
	
	public CandidateEmailVerification(int candidateId) {
		super();
		this.candidateId = candidateId;
	}



}
