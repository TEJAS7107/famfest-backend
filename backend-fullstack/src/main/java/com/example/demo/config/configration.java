package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.demo.filter.Jwt_Filter;
import com.example.demo.service.UserService;

@Component
@EnableWebSecurity
public class configration {

	@Autowired
	private Jwt_Filter authFilter;

	@Bean
	public UserDetailsService userDeatails() {

		return new UserService();
	}

	@Bean
	public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().cors().and()
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("api/authentication/test", "api/authentication/register",
								"api/authentication/login", "api/famfest/**", "api/Message/user_message",
								"api/authentication/**")
						.permitAll()
						.requestMatchers("api/authentication/**", "api/event/**", "api/Message/**", "api/payment/**")
						.authenticated()

				).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
		// ).formLogin().and().build();
	}

	@Bean
	public PasswordEncoder passencoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDeatails());
		auth.setPasswordEncoder(passencoder());

		return auth;

	}

	@Bean
	public AuthenticationManager auth(AuthenticationConfiguration config) throws Exception {
		// return config.getAuthenticationManager();
		return config.getAuthenticationManager();
	}

}
