package br.com.sistema.security.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.sistema.exceptions.DadosInvalidosException;
import br.com.sistema.exceptions.UsuarioNaoEncontradoException;
import br.com.sistema.repository.UsuarioRepository;
import br.com.sistema.security.TokenService;
import br.com.sistema.security.dto.LoginRequestDTO;
import br.com.sistema.security.dto.TokenResponseDto;

@Service
public class LoginService {

	private Logger logger = Logger.getLogger(LoginService.class.getName());
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public ResponseEntity<TokenResponseDto> efetuaLogin(LoginRequestDTO data) { //(2) Método para autenticar o usuário e gerar um token de acesso
	    logger.info("5. Método efetuaLogin do LoginService foi iniciando."); // Registra no log que o método de autenticação foi iniciado
	    try {
	        var username = data.username(); // Obtém o nome de usuário das credenciais fornecidas
	        var password = data.password(); // Obtém a senha das credenciais fornecidas
	        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username, password) ); // Autentica o usuário com a classe UsuarioService, por implementar UserDetailsService 
	        var usuario = usuarioRepository.findByUsername(username); // Busca o usuário no banco de dados usando o username da request
	        
	        if (usuario != null) { // Verifica se o usuário foi encontrado no banco de dados
	        	var tokenDto = tokenService.criaTokenDeAcesso(username, usuario.getRoles()); // Gera um token de acesso se o usuário for encontrado, usando o nome de usuário e suas permissões (roles)
	        	return ResponseEntity.ok(tokenDto); // Retorna uma resposta HTTP 200 (OK) com o token gerado
	        } else {
	            throw new UsuarioNaoEncontradoException("Usuário " + username + " não encontrado."); // Se o usuário não for encontrado, lança uma exceção informando o erro
	        }
	    } catch (Exception e) { // Captura qualquer exceção que ocorra durante o processo de autenticação
	        throw new DadosInvalidosException("usuário ou senha inválidos."); // Lança uma exceção informando que os dados de autenticação são inválidos
	    }
	}

}
