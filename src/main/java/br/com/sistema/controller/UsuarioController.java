package br.com.sistema.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.sistema.dto.CriarUsuarioDTO;
import br.com.sistema.dto.UsuarioFotoResponseDTO;
import br.com.sistema.model.Usuario;
import br.com.sistema.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
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
    
    
    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Retorna os dados de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorId(
            @Parameter(description = "ID do Usuario")
            @PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário")
            @PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PutMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload foto de perfil", description = "Atualiza foto do usuário como Base64")
    public ResponseEntity<UsuarioFotoResponseDTO> uploadFotoPerfil(
            @PathVariable Long id,
            @RequestPart("foto") MultipartFile foto) {

        UsuarioFotoResponseDTO response = usuarioService.atualizarFotoPerfil(id, foto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/foto")
    @Operation(summary = "Obter foto de perfil")
    public ResponseEntity<String> obterFotoPerfil(@PathVariable Long id) {
        String fotoBase64 = usuarioService.obterFotoPerfil(id);
        return fotoBase64 != null 
            ? ResponseEntity.ok(fotoBase64) 
            : ResponseEntity.notFound().build();
    }
}
