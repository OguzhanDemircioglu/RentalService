package com.etiya.RentACarSpringProject.core.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperManager implements ModelMapperService {
	private ModelMapper modelMapper;
	
	@Autowired
	public ModelMapperManager(ModelMapper modelMapper) {
		
		super();
		this.modelMapper= modelMapper;
		 
	}
	@Override
	public ModelMapper forDto() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
	
	@Override
	public ModelMapper forRequest() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper;
	}
	
	
	
	

}
