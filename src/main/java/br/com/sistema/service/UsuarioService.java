package br.com.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.sistema.model.Usuario;
import br.com.sistema.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean existsByUsernameOrEmail(String username, String email) {
        return usuarioRepository.existsByUsernameIgnoreCase(username) ||
               usuarioRepository.existsByEmailIgnoreCase(email);
    }
}
