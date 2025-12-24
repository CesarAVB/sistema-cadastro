package br.com.sistema.security.dto;

import java.util.Date;

public record TokenResponseDto(String username, Boolean authenticated, Date created, Date expiration, String accessToken) {}
