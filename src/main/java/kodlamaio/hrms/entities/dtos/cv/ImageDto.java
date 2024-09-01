package kodlamaio.hrms.entities.dtos.cv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
	private String imagePath;
	private String imageName;
}
