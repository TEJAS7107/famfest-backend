package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LoginData;
//import com.example.demo.model.UserInformation;
import com.example.demo.model.userinfo;


@Component
public class UserService implements UserDetailsService{
	
	@Autowired
	private LoginData data;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<userinfo> rawdata = data.findByEmail(username);
		System.out.println(rawdata);
		
		return rawdata.map(UserInfoService::new).orElseThrow(()->new UsernameNotFoundException("Invaild User"));
		
	}

}
