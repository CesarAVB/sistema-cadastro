package br.com.sistema.security;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class TokenFilter extends GenericFilterBean { // Classe que estende GenericFilterBean para criar um filtro personalizado para autenticação via JWT

	private Logger logger = Logger.getLogger(TokenFilter.class.getName());
	
	@Autowired
	private TokenService tokenService;

	public TokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override // ###### METODO PRINCIPAL QUE INTERCEPTA REQUISIÇÃO HTTP E APLICA O FILTRO CUSTOMIZADO #####
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
		logger.info("1. Método doFilter interceptou a requisição."); 
		String tokenRequisicao = tokenService.getAuthorizationRequisicao( (HttpServletRequest) request ); // Tenta recuperar o token JWT da requisição HTTP (cabeçalho "Authorization")
		var usernameSubject = tokenService.decodificaToken(tokenRequisicao);	// Decodifica o token JWT (tokenRequisicao) usando o método decodificaToken do tokenService e armazena o "subject"(usuário) na variável usernameSubject
		
		// Verifica se o token da requisição (tokenRequisicao) não é nulo e se o "subject" (usuário extraído do token) também não é nulo, ou seja, garante que há um token válido e que foi possível extrair o nome de usuário
		if (tokenRequisicao != null &&  usernameSubject != null) {
			logger.info("4. Token válido. Prosseguindo para autenticação."); 
			Authentication authentication = tokenService.obterAutenticacaoUsuario(tokenRequisicao); // Obtém a autenticação associada ao token (usuário e permissões)
			if (authentication != null) { // Verifica se a autenticação foi bem-sucedida
				SecurityContextHolder.getContext().setAuthentication(authentication); // Define a autenticação no contexto de segurança do Spring
			}
		}
		
		// Se não houver autenticão ou a validação do authentication do header retornar null, seta o SecurityContextHolder como anonymous e prossegue.
		chain.doFilter(request, response); // Continua o processamento da cadeia de filtros, passando a requisição e resposta adiante - 
	}
}
