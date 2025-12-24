package br.com.sistema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sistema.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.username =:username")	//JPQL que busca um usuário no BD. Não é necessário utilizar. Utilizamos apenas para praticar.
	Usuario findByUsername(@Param("username") String username);
	
//	Optional<Usuario> findByUsername(String username);
	
	Optional<Usuario> findByUsernameOrEmail(String username, String email);
	
}
