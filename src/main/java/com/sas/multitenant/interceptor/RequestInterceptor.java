package com.sas.multitenant.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.multitenant.constant.Constant;
import com.sas.multitenant.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor, Constant {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        log.info("In preHandle we are Intercepting the Request");
        String requestURI = request.getRequestURI();
        log.info("SERVER {} and PORT : {}", request.getServerName(), request.getServerPort());
        String tenantID = request.getHeader("x-tenant-id");
        log.info("RequestURI::" + requestURI + " || Search for X-TenantID  :: " + tenantID);
        if (tenantID == null) {
            response.getWriter().write(restResponseBytes(new Response("Invalid tenant identifier", HttpStatus.BAD_REQUEST)));
            response.setHeader("Content-Type", "application/json");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

    private String restResponseBytes(Response response) throws IOException {
        return new ObjectMapper().writeValueAsString(response);
    }

}
