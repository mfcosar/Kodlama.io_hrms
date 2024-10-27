package kodlamaio.hrms.entities.concretes;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="job_advertisements") //Buradaki s'yi unuttuğum için en az 2 saattir bütün 1 to Many relationları entityden api'ye kadar kontrol ettim..18.8.2024
@AllArgsConstructor
@NoArgsConstructor
public class JobAdvertisement {
	//Not null constraint yazılmadı henüz
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	//@Column(name="job_title_id")
	//private int jobTitleId;
	@ManyToOne()
	@JoinColumn(name="job_title_id")
	private Job job;
	
	//@Column(name="employer_id")
	//private int employerId;
	@ManyToOne()
	@JoinColumn(name="employer_id")
	private Employer employer;
	
	
	//@Column(name="city_id")
	//private int cityId;
	@ManyToOne()
	@JoinColumn(name="city_id")
	private City city;
	
	@ManyToOne()
	@JoinColumn(name="working_time_id")
	private WorkingTime workingTime;
	
	@ManyToOne()
	@JoinColumn(name="working_type_id")
	private WorkingType workingType;

	@Column(name="description")
	private String description;
	
	@Column(name="min_salary")
	private int minSalary;
	
	@Column(name="max_salary")
	private int maxSalary;
	
	
	@Column(name="open_position_amount")
	private int openPositionAmount;
	
	
	@Column(name="last_application_date")
	private Date lastApplicationDate;
	
	@Column(name="publication_date")
	private Date publicationDate;
	
	@Column(name="is_active")
	private boolean isActive=true;
	
	@Column(name="is_confirmed")
	private boolean isConfirmed=false;
	
}
