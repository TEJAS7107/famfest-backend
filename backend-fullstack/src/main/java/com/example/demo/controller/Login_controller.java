package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.realm.UserDatabaseRealm.UserDatabasePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.LoginData;
import com.example.demo.dto.ResetPasswordResponse;
import com.example.demo.model.userinfo;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.services.EmailSendService;
import com.example.demo.services.OtpGenerationService;
import com.example.demo.services.PasswordResetService;
import com.example.demo.services.UserRegisterOtpService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/authentication")
public class Login_controller {
	@Value("${twilio.accountSid}")
	private String accountSid;

	@Value("${twilio.authToken}")
	private String authToken;

	@Value("${twilio.phoneNumber}")
	private String twilioPhoneNumber;

	@Autowired
	LoginData info;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserService se;

	@Autowired
	OtpGenerationService otpGenerationService;

	@Autowired
	PasswordResetService passwordResetService;

	@Autowired
	UserRegisterOtpService userRegisterOtpService;

	@Autowired
	EmailSendService emailSendService;

	@GetMapping("/test")
	public String test() {

		return "heloo";
	}

	@PostMapping("/register")
	public String register(@RequestBody userinfo infor) throws Exception {

		boolean ifAlreadyAUser = info.existsByEmail(infor.getEmail());
		if (ifAlreadyAUser) {
			throw new Exception();
		}

		infor.setDate(new Date());
		infor.setId((UUID.randomUUID().toString().split("-")[0]));
		infor.setPassword(encoder.encode(infor.getPassword()));

		info.save(infor);

		return "User Created Succesfully";

	}

	@PostMapping("/login")
	public ResponseEntity<HashMap<Object, Object>> LoginToken(@RequestBody userinfo infoo) {
		List<Object> list = new ArrayList<>();
		HashMap<Object, Object> map = new HashMap<>();
		UserDetails loadUserByUsername = se.loadUserByUsername(infoo.getEmail());
		Optional<userinfo> userdata = info.findByEmail(infoo.getEmail());
		if (loadUserByUsername.getUsername().length() > 0 && loadUserByUsername.isAccountNonExpired()
				&& loadUserByUsername.isAccountNonLocked() && loadUserByUsername.isCredentialsNonExpired()) {
			if (encoder.matches(infoo.getPassword(), loadUserByUsername.getPassword())) {
				list.add(userdata.get().getId());
				list.add(userdata.get().getName());
				list.add(userdata.get().getRole());
				map.put("id", userdata.get().getId());
				map.put("name", userdata.get().getName());
				map.put("role", userdata.get().getRole());
				map.put("token", new JwtService().gettoken(infoo.getEmail()));

				// list.add(userdata.)
				// list.add(infoo.getName());
				// list.add();
				return ResponseEntity.ok(map);

			} else {
				// list.add("something went wrong");
				// map.put("error", "Something went wrong");
				throw new UsernameNotFoundException("Incorrect password");
			}
		} else {
			throw new UsernameNotFoundException("Invalid User");
		}

	}

	@GetMapping("/set_Otp/{id}")
	public Message setOtp(@PathVariable String id) {
		Twilio.init(accountSid, authToken);

		String otp = OtpGenerationService.generateOtp(6); // Generate a 6-digit OTP

		Message message = Message
				.creator(new PhoneNumber(id), new PhoneNumber(twilioPhoneNumber), "Your OTP is: " + otp).create();

		return message;

	}

	@GetMapping("/forgotPassword/{id}")
	public String sendotpForPasswordReset(@PathVariable String id) {
		if (info.existsByEmail(id)) {

			String res = passwordResetService.SendResetOtp(id);

			return res;

		}

		return null;

	}

	@PostMapping("/resetByEmail")
	public String resetPassword(@RequestBody ResetPasswordResponse resetPasswordResponse) {

		List<userinfo> data = info.findAll();
		for (userinfo cred : data) {
			if (cred.getEmail().equalsIgnoreCase(resetPasswordResponse.getEmail())) {
				cred.setPassword(encoder.encode(resetPasswordResponse.getNewPassword()));
				info.save(cred);

				return "password set successfully";
			}

			else {
				continue;
			}

		}
		return data.toString();

	}

	@GetMapping("UserDetails/{id}")
	public Optional<userinfo> getUserData(@PathVariable String id) {
		Optional<userinfo> user = info.findById(id);

		return user;

	}

	@GetMapping("VerifyMail/{id}")
	public String VerifyEmail(@PathVariable String id) {

		String otp = userRegisterOtpService.SendRegisterOtp(6);
		emailSendService.verifyMail(id, otp);

		return otp;

	}
}
