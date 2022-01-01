package com.etiya.RentACarSpringProject.ws.forCar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.ColorService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.ColorDto;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.CreateColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.DeleteColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.UpdateColorRequest;
import com.etiya.RentACarSpringProject.core.results.*;

@RestController
@RequestMapping("api/colors")
public class ColorController {
	ColorService colorService;

	@Autowired
	public ColorController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ColorDto>> getAll() {
		return this.colorService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<ColorDto> getById(int colorId) {
		return this.colorService.getById(colorId);
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateColorRequest createColorRequest) {
		return this.colorService.add(createColorRequest);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateColorRequest updateColorRequest) {
		return this.colorService.update(updateColorRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delte(DeleteColorRequest deleteColorRequest) {
		return this.colorService.delete(deleteColorRequest);
	}
}
