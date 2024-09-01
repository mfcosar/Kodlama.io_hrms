package kodlamaio.hrms.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateCvService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.CoverLetterDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.EducationInformationDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.ImageDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.LanguageInformationDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.TechnologyAbilityDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.WebAddressDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.WorkExperienceDao;


@Service
public class CandidateCvManager implements CandidateCvService{

	private CandidateDao candidateDao;
	private EducationInformationDao educationInformationDao;
	private WorkExperienceDao workExperienceDao;
	private LanguageInformationDao languageInformationDao;
	private ImageDao imageDao;
	private WebAddressDao webAddressDao;
	private TechnologyAbilityDao technologyAbilityDao;
	private CoverLetterDao coverLetterDao;
	
	@Autowired
	public CandidateCvManager(CandidateDao candidateDao, EducationInformationDao educationInformationDao,
			WorkExperienceDao workExperienceDao, LanguageInformationDao languageInformationDao, ImageDao imageDao,
			WebAddressDao webAddressDao, TechnologyAbilityDao technologyAbilityDao, CoverLetterDao coverLetterDao) {
		super();
		this.candidateDao = candidateDao;
		this.educationInformationDao = educationInformationDao;
		this.workExperienceDao = workExperienceDao;
		this.languageInformationDao = languageInformationDao;
		this.imageDao = imageDao;
		this.webAddressDao = webAddressDao;
		this.technologyAbilityDao = technologyAbilityDao;
		this.coverLetterDao = coverLetterDao;
	}

	@Override
	public DataResult<List<Object>> getCandidateCvByCandidateId(int candidateId) {

		List<Object> cvList = new ArrayList<>();
		cvList.add("Adayın kimlik bilgileri:");
		cvList.add(this.candidateDao.findById(candidateId));
		cvList.add("Adayın eğitim bilgileri:");
		cvList.add(this.educationInformationDao.findByCandidateIdAndIsGraduateFalse(candidateId));
		cvList.add(this.educationInformationDao.findByCandidateIdOrderByEndDateDesc(candidateId));
		cvList.add("Adayın iş tecrübesi bilgileri:");
		cvList.add(this.workExperienceDao.findByCandidateIdAndIsContinuedTrue(candidateId));
		cvList.add(this.workExperienceDao.findByCandidateIdOrderByEndDateDesc(candidateId));
		cvList.add("Adayın yabancı dil bilgileri:");
		cvList.add(this.languageInformationDao.findByCandidateId(candidateId));
		cvList.add("Adayın fotoğraf bilgileri:");
		cvList.add(this.imageDao.findByCandidateId(candidateId));	
		cvList.add("Adayın web hesap bilgileri:");
		cvList.add(this.webAddressDao.findByCandidateId(candidateId));
		cvList.add("Adayın teknoloji bilgileri:");
		cvList.add(this.technologyAbilityDao.findByCandidateId(candidateId));
		cvList.add("Adayın ön yazısı:");
		cvList.add(this.coverLetterDao.findByCandidateId(candidateId));
		
		return new SuccessDataResult<List<Object>> (cvList, "Adayın Cv bilgileri listelendi");
	}

}
