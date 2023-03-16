package com.sas.multitenant.config;

import com.sas.multitenant.entity.DataSourceDetail;
import com.sas.multitenant.repository.DataSourceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSource implements Serializable {

    private final HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private DataSourceDetailRepository dataSourceDetailRepository;

    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DataSourceDetail> configList = dataSourceDetailRepository.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceDetail dataSourceDetail : configList) {
            DataSource dataSource = getDataSource(dataSourceDetail.getName());
            result.put(dataSourceDetail.getName(), dataSource);
        }
        return result;
    }

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    private DataSource createDataSource(String name) {
        DataSourceDetail config = dataSourceDetailRepository.findByName(name);
        if (config != null) {
            return DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl()).build();
        }
        return null;
    }

}
