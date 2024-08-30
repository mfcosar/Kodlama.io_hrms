package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.LanguageInformationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.LanguageInformationDao;
import kodlamaio.hrms.entities.concretes.cv.LanguageInformation;

@Service
public class LanguageInformationManager implements LanguageInformationService{
	
	private LanguageInformationDao languageInformationDao;

	@Autowired
	public LanguageInformationManager(LanguageInformationDao languageInformationDao) {
		super();
		this.languageInformationDao = languageInformationDao;
	}

	@Override
	public Result add(LanguageInformation languageInformation) {
		this.languageInformationDao.save(languageInformation);
		return new SuccessResult("Yabancı dil bilgisi eklendi.");
	}

	@Override
	public DataResult<List<LanguageInformation>> listLanguageInformationOfCandidate(int candidateId) {
		
		return new SuccessDataResult<List<LanguageInformation>>(this.languageInformationDao.findByCandidateId(candidateId), 
				"Yabancı dil bilgisi listelendi");
	}

}
