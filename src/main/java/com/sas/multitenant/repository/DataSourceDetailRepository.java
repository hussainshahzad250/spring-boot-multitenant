package com.sas.multitenant.repository;

import com.sas.multitenant.entity.DataSourceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceDetailRepository extends JpaRepository<DataSourceDetail, Long> {
    DataSourceDetail findByName(String name);
}
