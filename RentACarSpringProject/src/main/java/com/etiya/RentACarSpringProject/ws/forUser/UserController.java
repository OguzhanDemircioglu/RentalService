package com.etiya.RentACarSpringProject.ws.forUser;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.CreateApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.DeleteApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.UpdateApplicationUserRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;


@RestController
@RequestMapping("api/users")
public class UserController {
	UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ApplicationUserDto>> getAll(){
		return this.userService.getAll(); 
	}
	
	@GetMapping("/getById")
	public DataResult<ApplicationUserDto> getById(int userId){
		return this.userService.getById(userId);
	}
		
	@PostMapping("/insert")
	public Result insert(@RequestBody CreateApplicationUserRequest createApplicationUserRequest){
		return this.userService.add(createApplicationUserRequest); 
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateApplicationUserRequest UpdateApplicationUserRequest){
		return this.userService.update(UpdateApplicationUserRequest); 
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteApplicationUserRequest deleteApplicationUserRequest){
		return this.userService.delete(deleteApplicationUserRequest); 
	}

}
