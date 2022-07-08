package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/api/authenticate").permitAll()
		.antMatchers(HttpMethod.POST, "/api/user").permitAll()
		.antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "api/user").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/user/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/user/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/api/instrument").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/instrument").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.PUT, "/api/instrument").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/instrument/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.DELETE, "/api/instrument/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/api/orders/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/api/cart").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.PUT, "/api/checkout").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.DELETE, "/api/clear").hasAnyRole("ADMIN", "USER")
		.anyRequest().authenticated()
		.and().sessionManagement()
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
