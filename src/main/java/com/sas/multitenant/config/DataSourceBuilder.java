package com.sas.multitenant.config;

import com.sas.multitenant.constant.Constant;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBuilder extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements Constant {

    boolean init = false;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ApplicationContext context;
    private final Map<String, DataSource> map = new HashMap<>();

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, dataSource);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }
}
