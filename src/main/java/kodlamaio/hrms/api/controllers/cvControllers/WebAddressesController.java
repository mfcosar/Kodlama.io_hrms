package kodlamaio.hrms.api.controllers.cvControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.cvServices.WebAddressService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.WebAddress;

@RestController
@RequestMapping("/api/webAddresses")
public class WebAddressesController {
	
	private WebAddressService webAddressService;

	@Autowired
	public WebAddressesController(WebAddressService webAddressService) {
		super();
		this.webAddressService = webAddressService;
	}
	
	@GetMapping("/findByCandidateId") 
	public DataResult<List<WebAddress>> findByCandidateId(@RequestParam int candidateId){
		return this.webAddressService.listWebAddressOfCandidate(candidateId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody WebAddress webAddress) {
		return this.webAddressService.add(webAddress);
	}
}
