package com.sas.multitenant.service;

import com.sas.multitenant.entity.City;
import com.sas.multitenant.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void save(City city) {
        cityRepository.save(city);
    }

    public List<City> getAll() throws SQLException {
        return cityRepository.findAll();
    }

    public City get(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name);
    }

    public void delete(String name) {
        cityRepository.deleteByName(name);
    }
}
