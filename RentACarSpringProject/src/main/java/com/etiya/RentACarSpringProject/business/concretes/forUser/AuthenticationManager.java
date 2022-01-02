package com.etiya.RentACarSpringProject.business.concretes.forUser;

import java.util.List;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.dataAccess.forUser.ApplicationUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.AuthenticationService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.CorporateCustomerService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.IndividualCustomerService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateLoginRequest;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
import com.etiya.RentACarSpringProject.entities.ApplicationUser;

import javax.print.attribute.standard.MediaSize;

@Service
public class AuthenticationManager implements AuthenticationService {

	private UserService userService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private ApplicationUserDao applicationUserDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public AuthenticationManager(UserService userService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService, ApplicationUserDao applicationUserDao,
								 ModelMapperService modelMapperService, Environment environment, LanguageWordService languageWordService) {
		super();
		this.userService = userService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.modelMapperService = modelMapperService;
		this.applicationUserDao = applicationUserDao;
		this.environment=environment;
		this.languageWordService=languageWordService;
	}

	@Override
	public Result individualCustomerRegister(

			CreateIndividualCustomerRegisterRequest createIndividualCustomerRegisterRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createIndividualCustomerRegisterRequest.getEmail()),
				confirmPassword(createIndividualCustomerRegisterRequest.getPassword(),
						createIndividualCustomerRegisterRequest.getPasswordConfirm()));

		if (result != null) {
			return result;
		}

		CreateIndividualCustomerRequest individualCustomerRequest = modelMapperService.forDto()
				.map(createIndividualCustomerRegisterRequest, CreateIndividualCustomerRequest.class);

		ApplicationUser appUser = this.createUser(createIndividualCustomerRegisterRequest.getEmail(),
				createIndividualCustomerRegisterRequest.getPassword()).getData();
		
		individualCustomerRequest.setApplicationUser(modelMapperService.forRequest()
				.map(appUser, ApplicationUserDto.class));

		this.individualCustomerService.add(individualCustomerRequest);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.Register,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result corporateCustomerRegister(CreateCorporateCustomerRegisterRequest createCorporateCustomerRequest) {

		var result = BusinessRules.run(checkIfEmailExists(createCorporateCustomerRequest.getEmail()),
				confirmPassword(createCorporateCustomerRequest.getPassword(),
						createCorporateCustomerRequest.getPasswordConfirm()));

		if (result != null) {
			return result;
		}

		CreateCorporateCustomerRequest corporateCustomerRequest = modelMapperService.forDto()
				.map(createCorporateCustomerRequest, CreateCorporateCustomerRequest.class);

		ApplicationUser appUser = this.createUser(createCorporateCustomerRequest.getEmail(),
				createCorporateCustomerRequest.getPassword()).getData();
		
		corporateCustomerRequest.setApplicationUser(modelMapperService.forDto()
				.map(appUser, ApplicationUserDto.class));

		this.corporateCustomerService.add(corporateCustomerRequest);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.Register,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result login(CreateLoginRequest createLoginRequest) {

		var result = BusinessRules.run(checkPassword(createLoginRequest),
				checkEmailAndPassword(createLoginRequest.getEmail()));

		if (result != null) {
			return result;
		}

        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.Login,Integer.parseInt(environment.getProperty("language"))));

	}

	private DataResult<ApplicationUser> createUser(String email, String password) {

		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setEmail(email);
		applicationUser.setPassword(password);

		this.userService.add(applicationUser);
		return new SuccessDataResult<ApplicationUser>(applicationUser);
	}
	
	private Result checkIfEmailExists(String registerEmail) {

		List<String> emails = this.userService.findAllEmail().getData();
		for (String email : emails) {
			if (registerEmail.equals(email)) {
		        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.ExistsUser,Integer.parseInt(environment.getProperty("language"))));

			}
		}
		return new SuccessResult();
	}

	private Result checkPassword(CreateLoginRequest createLoginRequest) {

		if (this.userService.getPasswordByEmail(createLoginRequest.getEmail()).getMessage().toString()
				.equals(createLoginRequest.getPassword() )) {
			return new SuccessResult();
		} else {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.IncorrectEntry,Integer.parseInt(environment.getProperty("language"))));

		}
	}

	private Result confirmPassword(String password, String passwordConfirm) {

		if (!password.equals(passwordConfirm)) {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.IncorrectPassword,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	private Result checkEmailAndPassword(String email) {
		if (!this.applicationUserDao.existsByEmail(email)) {

	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.LOGINEMAILERROR,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
