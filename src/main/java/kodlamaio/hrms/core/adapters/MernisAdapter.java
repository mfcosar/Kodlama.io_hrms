package kodlamaio.hrms.core.adapters;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class MernisAdapter {
	//simulation of Mernis
	
	public boolean checkIfrealPerson(Candidate candidate) { 
		//web servis client eklemede hata verdiği için simulasyon kullanıldı: IWAB0503E Unable to update Java build path. Please check your system environment.
		return true;
	}

}
