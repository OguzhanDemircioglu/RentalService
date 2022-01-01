package com.etiya.RentACarSpringProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="city_id")
    private int cityId;

    @Column(name="city_name")
    private String cityName;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Car> cars;

}
