package kodlamaio.hrms.api.controllers.cvControllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

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
		return this.imageService.findByCandidateId(candidateId);
	}
	
	/*@PostMapping("/addbyfile")
	public Result add(@RequestParam("file")MultipartFile file, @RequestParam int candidateId) throws IOException{
		return this.imageService.add(file, candidateId);
	}*/
	
	@PostMapping("/addbypath")
	public Result add(@RequestParam int candidateId, @RequestParam String filePath) throws IOException{
		return this.imageService.add(candidateId, filePath);
	}
	
}
