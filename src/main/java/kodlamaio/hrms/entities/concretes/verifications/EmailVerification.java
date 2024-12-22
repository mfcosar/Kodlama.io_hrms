package kodlamaio.hrms.entities.concretes.verifications;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)//base tablolara bunu ekledik
@Table(name="verification_codes")
public class EmailVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="verified")
	private boolean verified;
	
	@Column(name="verification_expiry")
	private LocalDateTime verificationExpiry;

	public EmailVerification(String code, boolean verified, LocalDateTime verificationExpiry) {
		super();
		this.code = code;
		this.verified = verified;
		this.verificationExpiry = verificationExpiry;
	}

	
	
}
