package br.com.sistema.security.dto;

import java.io.Serializable;
import java.util.Date;

public class TokenDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String accessToken;
//	private String refreshToken;

	public TokenDto() {
		// TODO Auto-generated constructor stub
	}

	public TokenDto(String username, Boolean authenticated, Date created, Date expiration, String accessToken) {
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accessToken = accessToken;
//		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

//	public String getRefreshToken() {
//		return refreshToken;
//	}
//
//	public void setRefreshToken(String refreshToken) {
//		this.refreshToken = refreshToken;
//	}

}
