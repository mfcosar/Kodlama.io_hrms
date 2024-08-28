package kodlamaio.hrms.api.controllers.cvControllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.cvServices.ImageService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.cv.Image;

@RestController
@RequestMapping("/api/images")
public class ImagesController {
	
	private ImageService imageService;
	@Autowired
	public ImagesController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}

	@GetMapping("/findByCandidateId") 
	public DataResult<List<Image>> findByCandidateId(@RequestParam int candidateId){
		return this.imageService.findByCandidate_Id(candidateId);
	}
	
	@PostMapping("/add")
	public Result add(@RequestParam int candidateId, @RequestParam String filePath) throws IOException{
		return this.imageService.add(candidateId, filePath);
	}
	
}
