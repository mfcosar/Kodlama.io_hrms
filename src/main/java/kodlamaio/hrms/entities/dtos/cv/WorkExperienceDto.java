package kodlamaio.hrms.entities.dtos.cv;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

	private String companyName;
	private String positionName;
	private Date beginDate;
	private Date endDate;
	private boolean isContinued;

}
