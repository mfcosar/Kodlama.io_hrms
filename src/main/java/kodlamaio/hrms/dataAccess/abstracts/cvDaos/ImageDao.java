package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.Image;
import kodlamaio.hrms.entities.dtos.cv.ImageDto;

public interface ImageDao extends JpaRepository<Image, Integer>{
	
	//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.ImageDto(i.imagePath, i.imageName ) "
			+ "FROM Image i JOIN i.candidate c WHERE c.id = :candidateId ")
	List<ImageDto> findImageDtosByCandidateId(int candidateId);
	
	List<Image> findByCandidateId(int candidateId);

}
