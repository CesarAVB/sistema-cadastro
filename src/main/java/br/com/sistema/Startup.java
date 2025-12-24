package br.com.sistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		
		// Cria uma senha criptografada
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String result = passwordEncoder.encode("26021988");
//		System.out.println("Senha criptografada: " + result);
	}

}
