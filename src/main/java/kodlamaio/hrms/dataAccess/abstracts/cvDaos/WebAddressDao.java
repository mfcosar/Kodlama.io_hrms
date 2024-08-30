package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cv.WebAddress;

public interface WebAddressDao extends JpaRepository<WebAddress, Integer> {
	
	List<WebAddress> findByCandidateId(int candidateId);

}
