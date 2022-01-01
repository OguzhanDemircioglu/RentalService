package com.etiya.RentACarSpringProject.business.requests.cityRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCityRequest {

    @JsonIgnore
   private int cityId;

    String cityName;
}
