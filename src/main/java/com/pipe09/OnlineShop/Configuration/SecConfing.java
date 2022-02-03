package com.pipe09.OnlineShop.Configuration;

import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecConfing extends WebSecurityConfigurerAdapter {
    private final MemberService service;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(service).passwordEncoder(service.getPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http
                //NEGATIVE WAY
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/loginproc").defaultSuccessUrl("/").successHandler(successHandler).failureHandler(failureHandler)
                .and().logout().invalidateHttpSession(true);


    }





}
