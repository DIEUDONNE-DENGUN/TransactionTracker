package org.iota.transactiontracking.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

/*
   @Author:Dieudonne Takougang
   @Date: 15/10/2021
   @Decsription: Allow cors exception for all api routes
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //disable CRSF for the api end point
        http.csrf().disable();
        //disable cors protection for the api endpoint using a bean
        http.cors().configurationSource(request -> disableCorsProtection());

        // use stateless communication
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/**").permitAll().antMatchers("/transactions/websocket/**").permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring()
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/**.js")//
                .antMatchers("/**.css")//
                .antMatchers("/transactions/websocket/**")

                // Un-secure H2 Database for testing purposes
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");
    }

    @Bean
    public CorsConfiguration disableCorsProtection() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*","http://localhost:4200","http://localhost:8081"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
        return corsConfiguration;
    }
}
