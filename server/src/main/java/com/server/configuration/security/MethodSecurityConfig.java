package com.server.configuration.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    public MethodInterceptor methodSecurityInterceptor(MethodSecurityMetadataSource methodSecurityMetadataSource) {
        return super.methodSecurityInterceptor(methodSecurityMetadataSource);
    }

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        return super.accessDecisionManager();
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return super.createExpressionHandler();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
