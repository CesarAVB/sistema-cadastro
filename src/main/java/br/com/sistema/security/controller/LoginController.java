package br.com.sistema.security.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.security.dto.LoginRequestDTO;
import br.com.sistema.security.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private Logger logger = Logger.getLogger(LoginService.class.getName());
	
	@Autowired
	private LoginService loginService;
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/login") // ()Define que este método será chamado quando houver uma requisição HTTP POST para o endpoint "/login"
	public ResponseEntity signin(@RequestBody LoginRequestDTO loginRequestDTO) { // Método de login que recebe as credenciais no corpo da requisição
		logger.info("4. Análise da Requisição finalizada. Método signin do LoginController foi iniciando.");
		if (validaParametros(loginRequestDTO)) { // Verifica se os parâmetros da requisição são válidos
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Parâmetros inválidos."); // Se os parâmetros forem inválidos, retorna status 403 (FORBIDDEN) e mensagem de erro
	    }
	    var token = loginService.efetuaLogin(loginRequestDTO); // Chama o serviço de login para gerar um token com as credenciais fornecidas
	    if (token == null) { // Verifica se o token gerado é nulo, indicando falha no login
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválidos."); // Se o token for nulo, retorna status 403 e mensagem de erro
	    }
	    return token; // Se o login for bem-sucedido, retorna o token gerado
	}

	// ###### METODO AUXILIAR QUE VALIDA OS PARAMETROS DE LOGIN PASSADOS ######
	private boolean validaParametros(LoginRequestDTO loginRequestDTO) {
	    return loginRequestDTO == null // Verifica se o objeto de credenciais é nulo
	        || loginRequestDTO.username() == null // Verifica se o nome de usuário é nulo
	        || loginRequestDTO.username().isBlank() // Verifica se o nome de usuário está em branco
	        || loginRequestDTO.password() == null // Verifica se a senha é nula
	        || loginRequestDTO.password().isBlank(); // Verifica se a senha está em branco
	}
}
