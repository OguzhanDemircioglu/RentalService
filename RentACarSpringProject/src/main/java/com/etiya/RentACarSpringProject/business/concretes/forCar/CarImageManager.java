package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.dataAccess.forCar.CarImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarImageService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.BrandDto;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarImageDto;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.UpdateCarImageRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorDataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
import com.etiya.RentACarSpringProject.entities.Brand;
import com.etiya.RentACarSpringProject.entities.Car;
import com.etiya.RentACarSpringProject.entities.CarImage;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public CarImageManager(CarImageDao carImageDao, ModelMapperService modelMapperService,Environment environment, LanguageWordService languageWordService) {
		super();
		this.carImageDao = carImageDao;
		this.modelMapperService = modelMapperService;
		this.environment = environment;
		this.languageWordService = languageWordService;
	}

	@Override
	public DataResult<List<CarImage>> findAll() {

        return new SuccessDataResult<List<CarImage>>(this.carImageDao.findAll(),languageWordService.getByLanguageAndKeyId(Messages.CarsImagesListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<List<CarImageDto>> getAll() {

	
        return new SuccessDataResult<List<CarImageDto>>(this.carImageDao.findAll().stream()
				.map(carImage -> modelMapperService.forDto().map(carImage, CarImageDto.class))
				.collect(Collectors.toList()),languageWordService
				.getByLanguageAndKeyId(Messages.CarsImagesListed,Integer
				.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<CarImage> getById(int carImageId) {

		var result= BusinessRules.run(checkIfCarImageIdExists(carImageId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

        return new SuccessDataResult<CarImage>(this.carImageDao.getById(carImageId), languageWordService.getByLanguageAndKeyId(Messages.GetCarImages,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest) {

		var result = BusinessRules.run(checkCarImageCount(createCarImageRequest.getCarId(), 5),
				checkCarImageFileType(createCarImageRequest.getImage()));

		if (result != null) {
			return result;
		}

		CarImage carImage = new CarImage();
		Car car = new Car();
		car.setCarId(createCarImageRequest.getCarId());
		carImage.setCar(car);
		carImage.setDate(this.getTodaysDate());
		carImage.setImagePath(
				"C:/Users/oguzhan.demircioglu/Desktop/Yeni klasör/ReCapProject/RentACarSpringProject/src/main/java/images"
				+ this.getRandomName() + ".jpg");

		this.saveImage(createCarImageRequest.getImage(), carImage.getImagePath());

		this.carImageDao.save(carImage);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CarImageAdded,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest) {

		var result = BusinessRules.run(checkCarImageCount(updateCarImageRequest.getCarId(), 5),
				checkCarImageFileType(updateCarImageRequest.getImage()),
						checkIfCarImageIdExists(updateCarImageRequest.getCarImageId()),
				checkIfCarIdExists(updateCarImageRequest.getCarId()));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		CarImage carImage = carImageDao.getById(updateCarImageRequest.getCarImageId());

		Car car = new Car();
		car.setCarId(updateCarImageRequest.getCarId());
		carImage.setCar(car);
		carImage.setDate(this.getTodaysDate());
		carImage.setImagePath(
				"C:/Users/oguzhan.demircioglu/Desktop/Yeni klasör/ReCapProject/RentACarSpringProject/src/main/java/images/"
						+ this.getRandomName() + ".jpg");

		this.saveImage(updateCarImageRequest.getImage(), carImage.getImagePath());

		this.carImageDao.save(carImage);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CarImageUpdated,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result delete(DeleteCarImageRequest deleteCarImageRequest) {

		var result= BusinessRules.run(checkIfCarImageIdExists(
				deleteCarImageRequest.getCarImageId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		CarImage carImage = this.carImageDao.getById(deleteCarImageRequest.getCarImageId());

		this.carImageDao.delete(carImage);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CarImageDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<List<CarImage>> findImagePathsByCarId(int carId) {

		var result= BusinessRules.run(checkIfCarIdExists(carId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		int limit = carImageDao.countByCar_CarId(carId);

		if (limit > 0) {
	        return new ErrorDataResult<List<CarImage>>(this.carImageDao.getByCar_CarId(carId),languageWordService.getByLanguageAndKeyId(Messages.GetCarImages,Integer.parseInt(environment.getProperty("language"))));

		}

		List<CarImage> carImages = new ArrayList<CarImage>();

		CarImage carImage = new CarImage();
		carImage.setImagePath("C:/Users/oguzhan.demircioglu/Desktop/Yeni klasör/ReCapProject/RentACarSpringProject/src/main/java/images/DefaultEtiya.jpg");

		carImages.add(carImage);
        return new SuccessDataResult<List<CarImage>>(carImages,languageWordService.getByLanguageAndKeyId(Messages.GetCarImages,Integer.parseInt(environment.getProperty("language"))));

	}
	
	@Override
	public DataResult<List<CarImageDto>> getImagePathsByCarId(int carId) {

		int limit = carImageDao.countByCar_CarId(carId);

		if (limit > 0) {

			List<CarImageDto> carImagesDto = this.carImageDao.getByCar_CarId(carId).stream()
					.map(carImage -> modelMapperService.forDto().map(carImage, CarImageDto.class))
							.collect(Collectors.toList());

	        return new ErrorDataResult<List<CarImageDto>>(carImagesDto,languageWordService.getByLanguageAndKeyId(Messages.GetCarImages,Integer.parseInt(environment.getProperty("language"))));

		}

		List<CarImageDto> carImagesDto = new ArrayList<CarImageDto>();

		CarImageDto carImageDto = new CarImageDto();
		carImageDto.setImagePath(
				"C:/Users/oguzhan.demircioglu/Desktop/Yeni klasör/ReCapProject/RentACarSpringProject/src/main/java/images/DefaultEtiya.jpg");
		carImagesDto.add(carImageDto);

        return new SuccessDataResult<List<CarImageDto>>(carImagesDto,languageWordService.getByLanguageAndKeyId(Messages.GetCarImages,Integer.parseInt(environment.getProperty("language"))));

	}

	private Result checkCarImageCount(int carId, long limit) {

		if (carImageDao.countByCar_CarId(carId) < limit) {
			return new SuccessResult();
		}
        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.GetCarImageLimit,Integer.parseInt(environment.getProperty("language"))));

	}

	private Result checkCarImageFileType(MultipartFile multipartFile) {

		if (!multipartFile.getContentType().toString().substring(multipartFile.getContentType().indexOf("/") + 1)
				.equals("jpeg")
				&& !multipartFile.getContentType().toString().substring(multipartFile.getContentType().indexOf("/") + 1)
				.equals("jpg")
				&& !multipartFile.getContentType().toString().substring(multipartFile.getContentType().indexOf("/") + 1)
				.equals("png")) {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.FormatError,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	private void saveImage(MultipartFile image, String imagePath) {

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ImageIO.write(bufferedImage, "jpg", new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Date getTodaysDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	private String getRandomName() {
		return java.util.UUID.randomUUID().toString();

	}

	private Result  checkIfCarImageIdExists(int carImageId){

		var result=this.carImageDao.existsById(carImageId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoCarImage,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	private Result  checkIfCarIdExists(int carId){

		var result=this.carService.checkIfCarIdExists(carId).isSuccess();
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoCar,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}


}
