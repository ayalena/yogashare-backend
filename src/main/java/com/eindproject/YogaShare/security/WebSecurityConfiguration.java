package com.eindproject.YogaShare.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //configures authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //step 2: save users in local memory (later: in database)
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}123456").roles("USER")
                .and()
                .withUser("admin").password("{noop}123456").roles("USER", "ADMIN")
                ;

    }


    //configures and secures endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                //define endpoints
                .antMatchers(HttpMethod.DELETE,"delete/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/{username}").hasRole("USER")
//                .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
    }
}
