package br.com.sistema.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.sistema.dto.UsuarioFotoResponseDTO;
import br.com.sistema.exceptions.DadosInvalidosException;
import br.com.sistema.exceptions.UsuarioNaoEncontradoException;
import br.com.sistema.model.Usuario;
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
	
	
	public UsuarioFotoResponseDTO atualizarFotoPerfil(Long id, MultipartFile foto) {
	    // Validações
	    if (foto.isEmpty()) {
	        throw new DadosInvalidosException("Arquivo não pode estar vazio");
	    }

	    if (!foto.getContentType().startsWith("image/")) {
	        throw new DadosInvalidosException("Apenas imagens são permitidas");
	    }

	    if (foto.getSize() > 5 * 1024 * 1024) { // 5MB
	        throw new DadosInvalidosException("Foto deve ter no máximo 5MB");
	    }

	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
	    if (usuarioOpt.isEmpty()) {
	        throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + id);
	    }

	    Usuario usuario = usuarioOpt.get();

	    // Converte MultipartFile → Base64
	    try {
	        String fotoBase64 = "data:" + foto.getContentType() + ";base64," + 
	                           Base64.getEncoder().encodeToString(foto.getBytes());
	        usuario.setFotoPerfil(fotoBase64);
	        usuarioRepository.save(usuario);

	        return new UsuarioFotoResponseDTO(
	            usuario.getId(), 
	            usuario.getNome(), 
	            fotoBase64,
	            "Foto atualizada com sucesso!"
	        );
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao processar foto: " + e.getMessage());
	    }
	}

	
	public String obterFotoPerfil(Long id) {
	    return usuarioRepository.findById(id)
	        .map(Usuario::getFotoPerfil)
	        .orElse(null);
	}


    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    
    public boolean existsByUsernameOrEmail(String username, String email) {
        return usuarioRepository.existsByUsernameIgnoreCase(username) ||
               usuarioRepository.existsByEmailIgnoreCase(email);
    }
    
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    
    public void remover(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new DadosInvalidosException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

}
