package com.etiya.RentACarSpringProject.business.abstracts.forUser;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.CreateApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.DeleteApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.UpdateApplicationUserRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.ApplicationUser;

public interface UserService {
	
	DataResult<List<ApplicationUser>> findAll();
	
	DataResult<List<ApplicationUserDto>> getAll();
	
	DataResult<ApplicationUser> findById(int applicationUserId);
	
	DataResult<ApplicationUserDto> getById(int applicationUserId);
	
	DataResult<List<String>> findAllEmail();
	
	Result add(ApplicationUser applicationUser);
	
	Result add(CreateApplicationUserRequest createApplicationUserRequest);

	Result update(UpdateApplicationUserRequest updateApplicationUserRequest);

	Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest);
	
	Result getPasswordByEmail(String email);

	Result  checkIfUserIdExists(int userId);
}
