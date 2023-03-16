package com.sas.multitenant.interceptor;

import com.sas.multitenant.constant.Constant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver, Constant {



    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = TenantContext.getCurrentTenant();
        if (t != null) {
            return t;
        } else {
            return DEFAULT_TENANT_ID;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
