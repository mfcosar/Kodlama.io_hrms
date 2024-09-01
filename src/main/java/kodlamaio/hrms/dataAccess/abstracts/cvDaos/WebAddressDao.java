package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.WebAddress;
import kodlamaio.hrms.entities.dtos.cv.WebAddressDto;

public interface WebAddressDao extends JpaRepository<WebAddress, Integer> {
	
//Dtos:
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.WebAddressDto(wa.githubAddress, wa.linkedInAddress ) "
			+ "FROM WebAddress wa JOIN wa.candidate c WHERE c.id = :candidateId ")
	List<WebAddressDto> findWebAddressDtoByCandidateId(int candidateId);
	//	List<WebAddress> findByCandidateId(int candidateId);
}
