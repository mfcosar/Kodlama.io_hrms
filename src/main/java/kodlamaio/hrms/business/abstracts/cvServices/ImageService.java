package kodlamaio.hrms.business.abstracts.cvServices;

import java.io.IOException;
import java.util.List;

//import org.springframework.web.multipart.MultipartFile;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.Image;

public interface ImageService {
	
	DataResult<List<Image>> getAll();
	Result add(int candidateId, String filePath) throws IOException;
	//Result add(MultipartFile file, int candidateId) throws IOException;
	DataResult<List<Image>> findByCandidateId(int candidateId);

}
