package br.com.sistema.dto;

public record UsuarioFotoResponseDTO(
    Long id,
    String nome,
    String fotoPerfil,
    String mensagem
) {}