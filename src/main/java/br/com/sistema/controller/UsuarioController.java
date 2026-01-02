package br.com.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import br.com.sistema.dto.CriarUsuarioDTO;
import br.com.sistema.model.Usuario;
import br.com.sistema.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody CriarUsuarioDTO dto) {
        try {
            // Verifica se já existe
            if (usuarioService.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail())) {
                return ResponseEntity.badRequest()
                    .body("Usuário ou email já cadastrado!");
            }

            // Cria usuário
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setUsername(dto.getUsername());
            usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 Hash seguro
            usuario.setFotoPerfil(dto.getFotoPerfil());
            usuario.setTema(dto.getTema());
            // Campos default já estão true na entidade

            usuarioService.salvar(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário criado com sucesso! ID: " + usuario.getId());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Erro ao criar usuário: " + e.getMessage());
        }
    }
}
