package br.com.sistema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpfCnpj(String cpfCnpj);
	
	// Contar clientes por data de cadastro
	Long countByDataCadastro(LocalDateTime dataCadastro); 

    // Buscar clientes ordenados por ID decrescente (mais recentes)
    List<Cliente> findTop10ByOrderByIdDesc();
	
}
