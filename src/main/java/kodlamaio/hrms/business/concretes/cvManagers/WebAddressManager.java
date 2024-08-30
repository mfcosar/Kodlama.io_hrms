package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.WebAddressService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.WebAddressDao;
import kodlamaio.hrms.entities.concretes.cv.WebAddress;

@Service
public class WebAddressManager implements WebAddressService{

	private WebAddressDao webAddressDao;
	@Autowired
	public WebAddressManager(WebAddressDao webAddressDao) {
		super();
		this.webAddressDao = webAddressDao;
	}
	
	@Override
	public DataResult<List<WebAddress>> listWebAddressOfCandidate(int candidateId) {
		return new SuccessDataResult<List<WebAddress>>(this.webAddressDao.findByCandidateId(candidateId), "Adaya ait web hesap bilgileri listelendi");
	}

	@Override
	public Result add(WebAddress webAddress) {
		this.webAddressDao.save(webAddress);
		return new SuccessResult("Web adres bilgileri eklendi.");
	}

}
