package kodlamaio.hrms.business.concretes.cvManagers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

import kodlamaio.hrms.business.abstracts.cvServices.ImageService;
import kodlamaio.hrms.core.adapters.cloudinary.FileService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.cvDaos.ImageDao;
import kodlamaio.hrms.entities.concretes.cv.Image;
import kodlamaio.hrms.entities.concretes.cv.enums.StorageType;
import kodlamaio.hrms.entities.dtos.cv.ImageDto;

@Service
public class ImageManager implements ImageService{
	
	private ImageDao imageDao;
	private CandidateDao candidateDao;
	private FileService cloudinaryFileService;

	@Autowired
	public ImageManager(ImageDao imageDao, FileService cloudinaryFileService, CandidateDao candidateDao) {
		super();
		this.imageDao = imageDao;
		this.cloudinaryFileService = cloudinaryFileService;
		this.candidateDao= candidateDao;
	}

	@Override
	public DataResult<List<Image>> getAll() {
		return new SuccessDataResult<List<Image>>(this.imageDao.findAll(), "Data Listelendi");
	}

	
	@Override
	public Result add(int candidateId, String filePath) throws IOException {
		// first form file from path info then upload file to cloudinary
		File file = new File(filePath);
		String path= this.cloudinaryFileService.upload(file);
		
		//add new image to DB
		Image image = new Image();
		image.setCandidate(this.candidateDao.findById(candidateId).get());
		image.setImagePath(path);
		image.setStorageName(StorageType.Cloudinary);
		image.setImageName(this.candidateDao.findById(candidateId).get().getFirstName() + this.candidateDao.findById(candidateId).get().getBirthYear());
		this.imageDao.save(image);
		
		return new SuccessResult("Resim Cloudinary'e yüklendi. Linki: " + path);
	}
	@Override
	public DataResult<List<ImageDto>> findImageDtosByCandidateId(int candidateId) {
		return new SuccessDataResult<List<ImageDto>>(this.imageDao.findImageDtosByCandidateId(candidateId), "Data Listelendi");
	}
/*	@Override
	public Result add(MultipartFile file, int candidateId) throws IOException {
		
		String path= this.cloudinaryFileService.upload(file);
		//add new image to DB
		Image image = new Image();
		image.setCandidate(this.candidateDao.findById(candidateId).get());
		image.setImagePath(path);
		image.setStorageName(StorageType.Cloudinary);
		image.setImageName(this.candidateDao.findById(candidateId).get().getFirstName() + this.candidateDao.findById(candidateId).get().getBirthYear());
		this.imageDao.save(image);
		
		return new SuccessResult("Resim Cloudinary'e yüklendi");	}*/

	

	/*@Override	}*/
	public DataResult<List<Image>> findByCandidateId(int candidateId) {
		
		return new SuccessDataResult<List<Image>>(this.imageDao.findByCandidateId(candidateId), "Data Listelendi");
	}
	
	/*public DataResult<Image> findByCandidateIdFirst(int candidateId) {
		
		return new SuccessDataResult<Image>(this.imageDao.findByCandidateId(candidateId).get(0), "Data Listelendi");
	}*/
	
}
