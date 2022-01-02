package com.etiya.RentACarSpringProject.business.concretes.forUser;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forUser.ApplicationUserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.etiya.RentACarSpringProject.business.dtos.forUser.IndividualCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.CreateApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.DeleteApplicationUserRequest;
import com.etiya.RentACarSpringProject.business.requests.applicationUserRequest.UpdateApplicationUserRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.ApplicationUser;

@Service
public class UserManager implements UserService {

	private ApplicationUserDao applicationUserDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public UserManager(ApplicationUserDao applicationUserDao, ModelMapperService modelMapperService, Environment environment, LanguageWordService languageWordService) {
		super();
		this.applicationUserDao = applicationUserDao;
		this.modelMapperService = modelMapperService;
		this.environment = environment;
		this.languageWordService = languageWordService;
	}

	@Override
	public DataResult<List<ApplicationUser>> findAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.applicationUserDao.findAll(), Messages.UsersListed);
	}

	@Override
	public DataResult<List<ApplicationUserDto>> getAll() {
		List<ApplicationUser> applicationUsers = this.applicationUserDao.findAll();
		List<ApplicationUserDto> applicationUsersDto = applicationUsers.stream()
				.map(applicationUser -> modelMapperService.forDto().map(applicationUser, ApplicationUserDto.class))
				.collect(Collectors.toList());

        return new SuccessDataResult<List<ApplicationUserDto>>(applicationUsersDto ,languageWordService.getByLanguageAndKeyId(Messages.UsersListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<ApplicationUser> findById(int applicationUserId) {

		var result=BusinessRules.run(checkIfUserIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

        return new SuccessDataResult<ApplicationUser>(this.applicationUserDao.getById(applicationUserId), languageWordService.getByLanguageAndKeyId(Messages.GetUser,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<ApplicationUserDto> getById(int applicationUserId) {

		var result=BusinessRules.run(checkIfUserIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		ApplicationUser applicationUser = this.applicationUserDao.getById(applicationUserId);
        return new SuccessDataResult<ApplicationUserDto>(modelMapperService.forDto().map(applicationUser, ApplicationUserDto.class), languageWordService.getByLanguageAndKeyId(Messages.GetUser,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<List<String>> findAllEmail() {
		return new SuccessDataResult<List<String>>(this.applicationUserDao.findAllEmail(),"");
	}

	@Override
	public Result add(ApplicationUser applicationUser) {
		var result = BusinessRules.run(checkIfEmailExists(applicationUser.getEmail()));

		if (result != null) {
			return result;
		}

		this.applicationUserDao.save(applicationUser);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.UserAdded,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result add(CreateApplicationUserRequest createApplicationUserRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createApplicationUserRequest.getEmail()));

		if (result != null) {
			return result;
		}
		
		ApplicationUser applicationUser = modelMapperService.forDto().map(createApplicationUserRequest, ApplicationUser.class);

		this.applicationUserDao.save(applicationUser);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.UserAdded,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result update(UpdateApplicationUserRequest updateApplicationUserRequest) {

		var check=BusinessRules.run(checkIfUserIdExists(updateApplicationUserRequest.getUserId()));

		if(check!=null){
			return  new ErrorDataResult(check);
		}

		ApplicationUser applicationUser = this.applicationUserDao
				.getById(updateApplicationUserRequest.getUserId());
		applicationUser.setEmail("");
		this.applicationUserDao.save(applicationUser);

		var result = BusinessRules.run(checkIfEmailExists(updateApplicationUserRequest.getEmail()));

		if (result != null) {
			return result;
		}
		
		ApplicationUser appUser = modelMapperService.forDto().map(updateApplicationUserRequest, ApplicationUser.class);

		this.applicationUserDao.save(appUser);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.UserUpdated,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest) {

		var result=BusinessRules.run(checkIfUserIdExists(deleteApplicationUserRequest.getUserId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		ApplicationUser applicationUser = this.applicationUserDao.getById(deleteApplicationUserRequest.getUserId());

		this.applicationUserDao.delete(applicationUser);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.UserDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result getPasswordByEmail(String email) {
		return new SuccessResult(this.applicationUserDao.getPasswordByEmail(email));
	}

	private Result checkIfEmailExists(String newEmail) {
		List<String> emails = this.applicationUserDao.findAllEmail();
		for (String email : emails) {
			System.out.println(email);
			if (newEmail.equals(email)) {
				return new ErrorResult();
			}
		}
		return new SuccessResult();
	}

	public Result  checkIfUserIdExists(int userId){
		var result=this.applicationUserDao.existsById(userId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoUser,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
