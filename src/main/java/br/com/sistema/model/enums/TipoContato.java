package br.com.sistema.model.enums;

public enum TipoContato {

	EMAIL("email"),
	TELEFONE("telefone");
	
	public String descricao;
	
	private TipoContato(String descricao) {
		this.descricao = descricao;
	}
	
}
