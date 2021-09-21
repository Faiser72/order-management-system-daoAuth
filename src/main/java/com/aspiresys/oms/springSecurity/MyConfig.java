package com.aspiresys.oms.springSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

//	password encoader
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	Dao auth provide
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		provideing userdetailsservice to DaoAuthenticationProvider
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

//	main thing(Configuration of methods 1. Authenticastion manager builder-> congigure nmethod

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		passing aauthenticationProvider to AuthenticationManagerBuilder

		auth.authenticationProvider(authenticationProvider()); // for database

//		for in memory
//		auth.inMemoryAuthentication()

	}

//	providing route, which user have which roles
//	from this method what we are telling is please dont protect all routes, which im providing you protect that. 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("SYSTEM_ADMIN").antMatchers("/user/**").hasRole("SYSTEM_USER")
				.antMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
	}

}
