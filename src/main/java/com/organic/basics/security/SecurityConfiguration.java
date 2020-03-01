package com.organic.basics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(new AccessControlService())
            .passwordEncoder(ConstantProvider.getPasswordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors().and().csrf().disable();

    httpSecurity.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth/**")
            .permitAll()
            .anyRequest().authenticated();

    httpSecurity.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }


}
