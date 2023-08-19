package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Jwt_Filter extends OncePerRequestFilter {
	@Autowired
	JwtService service;
	
	
	@Autowired
	UserService serv;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		
		String header = request.getHeader("Authorization");
		String token =null;
		String username=null;
		
		if (header!=null && header.startsWith("Bearer")) {
			
			token = header.substring(7);
			
			username = service.GetUsername(token);
			//Date date = service.getExpirationDate(token);
				
		}
		
		if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails deatils = serv.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(deatils, null,deatils.getAuthorities());
			
			authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authtoken);
		}
		
		filterChain.doFilter(request, response);
}

}
