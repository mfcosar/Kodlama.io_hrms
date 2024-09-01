package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.EducationInformationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.EducationInformationDao;
import kodlamaio.hrms.entities.concretes.cv.EducationInformation;
import kodlamaio.hrms.entities.dtos.cv.EducationInformationDto;

@Service
public class EducationInformationManager implements EducationInformationService {
	
	private EducationInformationDao educationInformationDao;

	@Autowired
	public EducationInformationManager(EducationInformationDao educationInformationDao) {
		super();
		this.educationInformationDao = educationInformationDao;
	}

	@Override
	public Result add(EducationInformation educationInformation) {
		this.educationInformationDao.save(educationInformation);
		return new SuccessResult("Okul bilgisi eklendi");
	}
	
	@Override
	public DataResult<List<EducationInformationDto>> listEducationInformationDtoOfCandidate(int candidateId) {
		// front end'de "devam ediyor" olarak görünecek ; mezun olunmamış okul en üstte
		List<EducationInformationDto> eduList = this.educationInformationDao.findEducationInformationDtoByCandidateIdAndIsGraduateFalse(candidateId);
		
		eduList.addAll(this.educationInformationDao.findEducationInformationDtoByCandidateIdOrderByEndDateDesc(candidateId));
		return new SuccessDataResult<List<EducationInformationDto>>(eduList, "Eğitim bilgileri mezuniyet yılına göre dizilip listelendi.");
	}

	/*@Override
	public DataResult<List<EducationInformation>> listEducationInformationOfCandidate(int candidateId) {
		// front end'de "devam ediyor" olarak görünecek ; mezun olunmamış okul en üstte
		List<EducationInformation> eduList = this.educationInformationDao.findByCandidateIdAndIsGraduateFalse(candidateId);
		
		eduList.addAll(this.educationInformationDao.findByCandidateIdOrderByEndDateDesc(candidateId));
		return new SuccessDataResult<List<EducationInformation>>(eduList, "Eğitim bilgileri mezuniyet yılına göre dizilip listelendi.");
	}*/



}
