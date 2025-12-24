package br.com.sistema.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private TokenService tokenService;
	
	@Bean
	public PasswordEncoder passwordEncoder() { 	// Define um bean de codificador de senhas para ser usado no sistema
	    return new BCryptPasswordEncoder(); 	// Retorna uma instância de BCryptPasswordEncoder, que é usada para criptografar senhas de maneira segura
	}

	@Bean
	public AuthenticationManager authenticationManagerTeste(AuthenticationConfiguration authenticationConfiguration) throws Exception { // Define um bean para o gerenciador de autenticação
	    return authenticationConfiguration.getAuthenticationManager(); // Retorna o gerenciador de autenticação configurado a partir do AuthenticationConfiguration
	}

	@Bean 
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); 					// Permite a origem da sua aplicação Angular
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); 	// Permite métodos HTTP
	    configuration.setAllowedHeaders(Arrays.asList("*")); 										// Permite todos os cabeçalhos
	    configuration.setAllowCredentials(true); 													// Permite o envio de credenciais (cookies, autenticação, etc.)
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 			// Cria um registro de configurações de CORS
	    source.registerCorsConfiguration("/**", configuration);										// Aplica a configuração a todas as URLs
	    return source; 																				// Retorna a configuração de CORS
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Define um bean que configura a cadeia de filtros de segurança da aplicação
	    TokenFilter filtroCustomizado = new TokenFilter(tokenService); // Cria um filtro personalizado baseado no provedor de token JWT
	    return http
	            .httpBasic(basic -> basic.disable()) 		// Desativa a autenticação HTTP básica
	            .csrf(csrf -> csrf.disable()) 				// Desativa a proteção CSRF (Cross-Site Request Forgery), pois a aplicação é stateless
	            .addFilterBefore(filtroCustomizado, UsernamePasswordAuthenticationFilter.class) 				// Adiciona o filtro JWT personalizado antes do filtro de autenticação padrão do Spring Security
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 	// Configura a gestão de sessão como "stateless", indicando que a aplicação não mantém estado de sessão
	            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
	                    .requestMatchers("/auth/login").permitAll() // Permite acesso público ao endpoint de login
	                    .anyRequest().authenticated()) 				// Exige autenticação para qualquer outra requisição
	            .cors(cors -> {}) 									// Habilita o suporte a CORS (Cross-Origin Resource Sharing)
	            .build(); 											// Constrói e retorna o objeto SecurityFilterChain configurado
	}

	
}
