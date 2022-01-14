package com.eindproject.YogaShare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public WebSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //configures authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //step 3: save users in database
        auth.jdbcAuthentication().dataSource(dataSource);

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
