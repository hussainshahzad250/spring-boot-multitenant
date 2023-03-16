package com.sas.multitenant.repository;

import com.sas.multitenant.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    City findByName(String name);

    void deleteByName(String name);
}
