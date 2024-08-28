package kodlamaio.hrms.business.abstracts.cvServices;

import java.io.IOException;
import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.Image;

public interface ImageService {
	
	DataResult<List<Image>> getAll();
	Result add(int candidateId, String filePath) throws IOException;
	DataResult<List<Image>> findByCandidate_Id(int candidateId);

}
