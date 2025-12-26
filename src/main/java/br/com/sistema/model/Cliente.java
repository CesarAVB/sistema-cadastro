package br.com.sistema.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import br.com.sistema.model.enums.Genero;
import br.com.sistema.model.enums.TipoPessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;
	
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	@Column(name = "rg")
	private String rg;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "genero")
	private Genero genero;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_cliente")
	private List<Endereco> enderecos;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_cliente")
	private List<Contato> contatos;

	@Column(name = "login")
	private String login;

	@Column(name = "senha")
	private String senha;

	@Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
	
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	
	
	@PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
