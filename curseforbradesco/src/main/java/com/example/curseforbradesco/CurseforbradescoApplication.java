package com.example.curseforbradesco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;

@SpringBootApplication
public class CurseforbradescoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurseforbradescoApplication.class, args);

//		Map<String, PasswordEncoder> econders = new HashMap<>();
//		econders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//		DelegatingPasswordEncoder passwordEncoder =
//				new DelegatingPasswordEncoder("pbkdf2",econders);
//
//
//		String result = passwordEncoder.encode("admin234");
//		System.out.println("My hash " + result);

	}

}


