package org.iota.transactiontracking.api.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.iota.transactiontracking.api.config.ApiKeyProp;
import org.iota.transactiontracking.api.domain.exception.AccessDeniedException;
import org.iota.transactiontracking.api.domain.exception.utils.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Allow access to the api to allow authorized client application using a spring filter chain.
 */
@Component
@Order(1)
public class ApiRequestFilter implements Filter {
    Logger apiSecurityLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiKeyProp apiKeyProp;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        //filter all protected endpoints with authorization key
        if (req.getRequestURI().contains("/protected")) {
            //extract api key set by client app on the request head from the authorization param
            String apiKeyValue = extractApiKey(req);
            //check if the apikey set on header same to application api access key, if not same, return unauthorized request
            if (!(apiKeyProp.getApiAccessKey().equals(apiKeyValue))) {
                apiSecurityLogger.info("Authentication  Failed  {} : {},", req.getMethod(), req.getRequestURI());
                throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractApiKey(HttpServletRequest req) {
        String apiKeyToken = req.getHeader("Authorization");
        if (apiKeyToken != null && apiKeyToken.startsWith("ApiKey ")) {
            return apiKeyToken.substring(7).toString();
        }
        return "defaultKey";
    }
}
