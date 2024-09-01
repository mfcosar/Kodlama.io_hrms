package kodlamaio.hrms.dataAccess.abstracts.cvDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.cv.EducationInformation;
import kodlamaio.hrms.entities.dtos.cv.EducationInformationDto;

public interface EducationInformationDao extends JpaRepository<EducationInformation, Integer>{
	
	//dtos:
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.EducationInformationDto(ei.schoolName, ei.department, ei.beginDate, ei.endDate, ei.isGraduate, ei.degree ) "
			+ "FROM EducationInformation ei JOIN ei.candidate c WHERE c.id = :candidateId ORDER BY ei.endDate DESC")
	List<EducationInformationDto> findEducationInformationDtoByCandidateIdOrderByEndDateDesc(int candidateId);
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.cv.EducationInformationDto(ei.schoolName, ei.department, ei.beginDate, ei.endDate, ei.isGraduate, ei.degree ) "
			+ "FROM EducationInformation ei JOIN ei.candidate c WHERE c.id = :candidateId AND ei.isGraduate = false  ORDER BY ei.endDate DESC")
	List<EducationInformationDto> findEducationInformationDtoByCandidateIdAndIsGraduateFalse(int candidateId);
	
	/*	List<EducationInformation> findByCandidateIdOrderByEndDateDesc(int candidateId);
	List<EducationInformation> findByCandidateIdAndIsGraduateFalse(int candidateId);*/
}
