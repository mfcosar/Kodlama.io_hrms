package kodlamaio.hrms.business.concretes.cvManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.cvServices.CoverLetterService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.CoverLetterDao;
import kodlamaio.hrms.entities.concretes.cv.CoverLetter;
import kodlamaio.hrms.entities.dtos.cv.CoverLetterDto;

@Service
public class CoverLetterManager implements CoverLetterService{
	
	private CoverLetterDao coverLetterDao;

	@Autowired
	public CoverLetterManager(CoverLetterDao coverLetterDao) {
		super();
		this.coverLetterDao = coverLetterDao;
	}

	@Override
	public Result add(CoverLetter coverLetter) {
		this.coverLetterDao.save(coverLetter);
		return new SuccessResult("Adaya ait ön yazı eklendi");
	}
	@Override
	public DataResult<List<CoverLetterDto>> listCoverLetterDtosOfCandidate(int candidateId) {
		return new SuccessDataResult<List<CoverLetterDto>> (this.coverLetterDao.findCoverLetterDtosByCandidateId(candidateId), 
				"Adaya ait ön yazılar listelendi");
	}
	/*@Override
	public DataResult<List<CoverLetter>> listCoverLetterOfCandidate(int candidateId) {
		return new SuccessDataResult<List<CoverLetter>> (this.coverLetterDao.findByCandidateId(candidateId), 
				"Adaya ait ön yazılar listelendi");
	}*/



}
