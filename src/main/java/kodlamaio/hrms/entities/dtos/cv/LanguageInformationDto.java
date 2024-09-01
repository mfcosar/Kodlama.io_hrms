package kodlamaio.hrms.entities.dtos.cv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageInformationDto {

	private String foreignLanguageName;
	private int foreignLanguageLevel;  // 1-5
}
