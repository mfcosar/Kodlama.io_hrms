package kodlamaio.hrms.business.abstracts.cvServices;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.WebAddress;
import kodlamaio.hrms.entities.dtos.cv.WebAddressDto;

public interface WebAddressService {
	
	//DataResult<List<WebAddress>> listWebAddressOfCandidate(int candidateId);
	Result add(WebAddress webAddress);
	
	//Dtos:
	DataResult<List<WebAddressDto>> listWebAddressDtoOfCandidate(int candidateId);

}
