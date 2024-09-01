package kodlamaio.hrms.entities.dtos.cv;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationInformationDto {

	private String schoolName;
	private String department;
	private Date beginDate;
	private Date endDate;
	private boolean isGraduate;
	private String degree;
}
