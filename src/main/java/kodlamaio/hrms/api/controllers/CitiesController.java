package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.CityService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.City;


@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CitiesController {
	
	private CityService cityService;

	@Autowired
	public CitiesController(CityService cityService) {
		super();
		this.cityService = cityService;
	}
	
	@GetMapping("/getall") //Buradaki A harfini büyk yazdığım için tam 2 gündür bütün projeyi DB scriptten tut entitylere kadar defalarca yeniden gözden geçirdim!-1.8.2024
	public DataResult<List<City>> getAll(){
		
		return this.cityService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody City city) {
		return this.cityService.add(city);
	}

}
