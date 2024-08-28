package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.hrms.entities.concretes.cv.Image;

public interface ImageDao extends JpaRepository<Image, Integer>{
	
	List<Image> findByCandidate_Id(int candidateId);

}
