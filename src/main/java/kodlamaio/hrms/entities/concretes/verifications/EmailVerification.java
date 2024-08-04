package kodlamaio.hrms.entities.concretes.verifications;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)//base tablolara bunu ekledik
@Table(name="verification_codes")
public class EmailVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="is_verified")
	private boolean isVerified;
	
	@Column(name="verification_date")
	private Date verificationDate;

	public EmailVerification() {
		super();
	}

	public EmailVerification(int id, String code, boolean isVerified, Date verificationDate) {
		super();
		this.id = id;
		this.code = code;
		this.isVerified = isVerified;
		this.verificationDate = verificationDate;
	}
	
	

}
