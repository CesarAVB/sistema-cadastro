package br.com.sistema.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import br.com.sistema.security.dto.TokenResponseDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

	private Logger logger = Logger.getLogger(TokenService.class.getName());
	
	@Value("${security.api.token.secret}") // Pega o valor da key definido no arquivo application.properties 
	private String secretKey;

	@Autowired
	private UserDetailsService userDetailsService;

	Algorithm algorithm = null;

	
	@PostConstruct
	protected void init() { // Método que inicializa a secretKey e o algoritmo de criptografia HMAC256 após a construção do bean
	    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // Codifica a chave secreta em Base64
	    algorithm = Algorithm.HMAC256(secretKey.getBytes()); // Define o algoritmo HMAC256 usando a chave secreta
	}

	
	// ###### METODO QUE CRIA E RETORNA UM TOKEN DE ACESSO APÓS CONFERIR AS INFORMACOES DE LOGIN ######
	public TokenResponseDto criaTokenDeAcesso(String username, List<String> roles) { 
		logger.info("8. Iniciando a criação do Token de Acesso."); 
	    try {
	    	Date dataGeracaoToken = Date.from( LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")) ); // Data/hora atual (momento da geração do token)
	    	Date validadeToken = Date.from( LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00")) ); // Data/hora de expiração do token (1 hora após o horário de criação)
		    String accessToken = gerarAccessToken(username, roles, dataGeracaoToken, validadeToken); // Gera o token de acesso com os dados do usuário
		    logger.info("10. Finalizando e retornando Token para o usuário."); 
		    return new TokenResponseDto(username, true, dataGeracaoToken, validadeToken, accessToken); // Retorna o TokenDto com o access token e suas informações
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro no processo de geração do token.");
		}
	}


	// ###### METODO AUXILIAR QUE CRIA O ACCESS TOKEN ######
	private String gerarAccessToken(String username, List<String> roles, Date dataGeracaoToken, Date validadeToken) { 
		logger.info("9. Gerando Token"); 
	    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(); // Obtém a URL do emissor do token (normalmente a URL da aplicação)
	    return JWT.create()
	    		.withClaim("roles", roles) 										// Adiciona as roles do usuário ao token
	            .withIssuedAt(dataGeracaoToken).withExpiresAt(validadeToken) 	// Define a data de emissão e expiração do token
	            .withSubject(username).withIssuer(issuerUrl) 					// Define o usuário e o emissor do token
	            .sign(algorithm).strip(); 										// Assina o token com o algoritmo HMAC256 e remove qualquer espaço em branco ao redor
	}


	// ###### DECODIFICA O TOKEN JWT, CARREGA DETALHES DO USUÁRIO E RETORNA UMA AUTENTICAÇÃO COM AS PERMISSÕES DO USUÁRIO ######
	public Authentication obterAutenticacaoUsuario(String token) { 
		logger.info("5. Realizando autenticação com username/password obtidos da requisição."); 
	    String tokenSubject = decodificaToken(token); // Decodifica o token JWT e guarda o subject(username) na variável para usar no método loadUserByUsername
	    UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenSubject); // Carrega os detalhes do usuário com base no nome de usuário decodificado do token
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); // Cria e retorna um objeto de autenticação com os dados do usuário e suas permissões (authorities)
	}

	
	// ###### METODO QUE PEGA O TOKEN ENVIADO PELA REQUISIÇÃO ######
	public String getAuthorizationRequisicao(HttpServletRequest request) { 	// Método utilizado/chamado no TokenFilter
		logger.info("2. Análise da Requisição iniciada. Método getAuthorizationRequisicao analisando o header em busca de Authorization."); 
	    String bearerToken = request.getHeader("Authorization"); 			// Obtém o cabeçalho "Authorization" da requisição
	    if(bearerToken != null && bearerToken.startsWith("Bearer ")) { 		// Verifica se o token está presente e começa com "Bearer "
	        return bearerToken.substring("Bearer ".length()); 				// Retorna o token sem o prefixo "Bearer "
	    }
	    logger.info("3. Authorization não encontrado. Retornando null.");
	    return null; // Se a requisição http não possuir Authorizarion e o token não estiver presente ou não seguir o formato esperado, retorna null
	}


	//###### DECODIFICA O TOKEN JWT, VALIDA O EMISSOR E OUTROS DADOS, E RETORNA O "SUBJECT" (USUÁRIO) ######
	public String decodificaToken(String token) {
	    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();	// Obtém a URL atual da aplicação (será usada como o emissor do token)
	    try {
	        String subjectToken = JWT.require(algorithm)	// Cria um verificador de token JWT com o algoritmo configurado e valida que o emissor do token é o esperado (issuerUrl)
	                                 .withIssuer(issuerUrl) // Especifica que o emissor do token deve ser o mesmo da aplicação
	                                 .build()               // Constrói o verificador
	                                 .verify(token)         // Verifica o token passado como argumento (valida assinatura, emissor, etc.)
	                                 .getSubject();         // Se o token for válido, obtém o 'subject' (geralmente o usuário do token)

	        return subjectToken; // Retorna o 'subject' do token, ou seja, a informação codificada como o "usuário" no token
	    } catch (Exception e) {
	        // Em caso de qualquer erro durante a verificação do token (token inválido, expirado, etc.), loga o erro
	        logger.severe("Erro ao decodificar o token. " + e.getMessage());
	        return null; // Retorna null se houver falha na validação do token
	    }
	}

}
