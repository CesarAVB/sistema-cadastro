package br.com.sistema.security.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.sistema.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService { // Classe que implementa a interface UserDetailsService para carregar os detalhes do usuário para autenticação
	
	private Logger logger = Logger.getLogger(UsuarioService.class.getName());

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Método que busca um usuário pelo nome de usuário e retorna os detalhes do usuário
	    logger.info("6. Buscando pelo usuário: " + username + " para autenticar."); // Registra no log que está buscando pelo usuário com o nome fornecido
	    var user = usuarioRepository.findByUsername(username); // Faz uma consulta ao repositório de usuários para encontrar o usuário pelo nome de usuário
	    if(user != null) { // Verifica se o usuário foi encontrado (não é nulo)
	    	logger.info("7. Username : " + username + " localizado. Retornando usuário.");
	    	return user; // Retorna os detalhes do usuário se ele foi encontrado
	    } else { // Se o usuário não foi encontrado
	        throw new UsernameNotFoundException("Usuário " + username + " não encontrado"); // Lança uma exceção indicando que o usuário não foi encontrado
	    }
	}

    

}
