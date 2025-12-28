package br.com.sistema.security;

import java.time.Instant;
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

    @Value("${security.api.token.secret}")
    private String secretKey;

    @Autowired
    private UserDetailsService userDetailsService;

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    // ###### MÉTODO QUE CRIA E RETORNA UM TOKEN DE ACESSO ######
    public TokenResponseDto criaTokenDeAcesso(String username, List<String> roles) {
        logger.info("Iniciando a criação do Token de Acesso.");
        try {
            Instant agora = Instant.now(); // Momento atual em UTC
            Date dataGeracaoToken = Date.from(agora);
            Date validadeToken = Date.from(agora.plusSeconds(3600)); // Expira em 1 hora

            String accessToken = gerarAccessToken(username, roles, dataGeracaoToken, validadeToken);

            logger.info("Token criado com sucesso.");
            return new TokenResponseDto(username, true, dataGeracaoToken, validadeToken, accessToken);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro no processo de geração do token.", e);
        }
    }

    // ###### MÉTODO AUXILIAR QUE GERA O TOKEN ######
    private String gerarAccessToken(String username, List<String> roles, Date dataGeracaoToken, Date validadeToken) {
        logger.info("Gerando Token...");
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(dataGeracaoToken)
                .withNotBefore(dataGeracaoToken) // Garantindo que o token pode ser usado imediatamente
                .withExpiresAt(validadeToken)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    // ###### DECODIFICA O TOKEN E RETORNA AUTENTICAÇÃO ######
    public Authentication obterAutenticacaoUsuario(String token) {
        logger.info("Autenticando usuário a partir do token.");
        String tokenSubject = decodificaToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenSubject);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // ###### OBTÉM O TOKEN DO HEADER Authorization ######
    public String getAuthorizationRequisicao(HttpServletRequest request) {
        logger.info("Analisando header Authorization.");
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        logger.info("Authorization não encontrado.");
        return null;
    }

    // ###### DECODIFICA O TOKEN E RETORNA O SUBJECT ######
    public String decodificaToken(String token) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        try {
            return JWT.require(algorithm)
                    .withIssuer(issuerUrl)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            logger.severe("Erro ao decodificar o token: " + e.getMessage());
            return null;
        }
    }
}
