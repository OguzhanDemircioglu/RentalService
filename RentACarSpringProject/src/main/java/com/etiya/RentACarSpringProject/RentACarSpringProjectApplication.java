package com.etiya.RentACarSpringProject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.etiya.RentACarSpringProject.core.results.ErrorDataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorResult;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestControllerAdvice
public class RentACarSpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarSpringProjectApplication.class, args);
			
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.etiya.RentACarSpringProject")).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exception) {

		Map<String, String> validationErrors = new HashMap<String, String>();

		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorDataResult<Object> error = new ErrorDataResult<Object>(validationErrors, "Validation Error");
		return error;
	}

	@ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleNoSuchElementException(NoSuchElementException exception){

        return new ErrorResult("No Value Found");
    }

	@ExceptionHandler(ParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleParseExceptionException(ParseException exception){

		return new ErrorResult("Tarih formatını DD-MM-YYYY şekli ile giriniz");
	}

	@ExceptionHandler(java.time.format.DateTimeParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleDateTimeParseException(java.time.format.DateTimeParseException exception){

		return new ErrorResult("Tarih formatını DD-MM-YYYY şekli ile giriniz");
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleNullPointerException(NullPointerException exception){

		return new ErrorResult("Var Olmayan bir değişken girdiniz");
	}

	@ExceptionHandler(ClassCastException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleClassCastException(ClassCastException exception){

		return new ErrorResult("Veri getirilemedi");
	}
}
