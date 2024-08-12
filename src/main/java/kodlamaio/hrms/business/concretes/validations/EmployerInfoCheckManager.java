package kodlamaio.hrms.business.concretes.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.validations.EmployerInfoCheckService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerInfoCheckManager implements EmployerInfoCheckService{
	
	private EmployerDao employerDao;


	@Autowired
	public EmployerInfoCheckManager(EmployerDao employerDao) {
		super();
		this.employerDao = employerDao;
	}

	@Override
	public Result checkAllFieldsEntered(Employer employer) {
		
		if (employer.getCompanyName().isEmpty())
			return new ErrorResult("Lütfen şirket ismini giriniz.");
		else if (employer.getWebAddress().isEmpty())
			return new ErrorResult("Lütfen web adresinizi giriniz.");
		else if (employer.getEmail().isEmpty())
			return new ErrorResult("Lütfen email adresinizi giriniz.");
		else if (employer.getPhoneNumber().isEmpty())
			return new ErrorResult("Lütfen telefon numaranızı giriniz.");
		else if (employer.getPassword().isEmpty())
			return new ErrorResult("Lütfen şifrenizi giriniz.");
		else 
			return new SuccessResult("Bütün alanlar eksiksiz girilmiş.");
	}

	@Override
	public Result checkWebAndEmailDomainIsSame(String webAddress, String email) {
		
		 String domainWeb = extractDomainFromWebAddress(webAddress);
		 String domainEmail = extractDomainFromEmail(email);
		 
		 if (domainWeb.equals(domainEmail))
			 return new SuccessResult("Web ve email domanileri aynı.");
		 else 
			 return new ErrorResult("Lütfen şirket domaine ait bir web ve email adresi giriniz.");

	}
	
	public static String extractDomainFromWebAddress(String url) {
		
	    int start = url.indexOf("://");
	    	    if (start < 0) {
	    	        start = 0;
	    	    } else {
	    	        start += 3;
	    	    }
	    	    int end = url.indexOf('/', start);
	    	    if (end < 0) {
	    	        end = url.length();
	    	    }
	    	    String domainName = url.substring(start, end);
	    	    return domainName;
	}

	public static String extractDomainFromEmail(String email) {
		
	    int start = email.indexOf("@");
	    	    if (start < 0) {
	    	        start = 0;
	    	    } 
	    	    
	    	    int end = email.length();

	    	    String domainName = email.substring(start, end);
	    	    return domainName;
	}
	@Override
	public Result checkEmailIsUnique(String email) {
		
		for(Employer x: this.employerDao.findAll()) {
			if (x.getEmail().equalsIgnoreCase(email)) {
				return new ErrorResult("Bu email adresi sisteme kayıtlı.");
			}
		}
		return new SuccessResult("Email sisteme kayıtlı değil");
	}

	@Override
	public Result isValidEmployer(Employer employer) {
		if (checkAllFieldsEntered(employer).isSuccess() && checkWebAndEmailDomainIsSame(employer.getWebAddress(), employer.getEmail()).isSuccess()
				&& checkEmailIsUnique(employer.getEmail()).isSuccess())
			return new SuccessResult("Bu şirketin bilgileri kayıt için uygundur.");
		
		else return new ErrorResult("Bu şirketin bilgileri kayıt için uygun değildir.");
	}

}
