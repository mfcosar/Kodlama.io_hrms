package kodlamaio.hrms.entities.concretes.cv;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kodlamaio.hrms.entities.concretes.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="language_informations")
@AllArgsConstructor
@NoArgsConstructor
public class LanguageInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne()
	@JoinColumn(name="candidate_id")
	Candidate candidate; 	//private int candidateId;
	
	@Column(name="foreign_language_name")
	private String foreignLanguageName;
	
	@Column(name="foreign_language_level")
	private int foreignLanguageLevel;  // 1-5

}