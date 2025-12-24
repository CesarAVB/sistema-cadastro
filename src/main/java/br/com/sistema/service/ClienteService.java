package br.com.sistema.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.sistema.dto.ClienteRequestDTO;
import br.com.sistema.dto.ClienteResponseDTO;
import br.com.sistema.exceptions.DadosInvalidosException;
import br.com.sistema.model.Cliente;
import br.com.sistema.repository.ClienteRepository;

@Service
//@Transactional //Se ocorrer uma exceção não verificada (ou seja, uma RuntimeException), o Spring fará o rollback automaticamente
public class ClienteService {

	private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;
    
	public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}

	
	public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {
		
		// Antes de salvar, verifica se o CPF/CNPJ passado existe no BD
		Optional<Cliente> clienteRetornado = clienteRepository.findByCpfCnpj(clienteRequestDTO.getCpfCnpj());
		
		// Se o CPF/CNPJ já existir no BD, lanca uma Excecao personalizada
		if (clienteRetornado.isPresent()) {
			throw new DadosInvalidosException("CPF/CNPJ já cadastrado no  sistema.");
		}
		
		// Se o CPF/CNPJ não existir no BD, executa as linhas abaixo
		// Cria um objeto Cliente
		var entity = convertToEntity(clienteRequestDTO);

		try {
			// Salva o cliente e converte o objeto retornado no método save em um ClienteResponseDTO 
			// Retorna o objeto ClienteResponseDTO para a ClienteController para ser exibida com um ResponseEntity
			var responseDTO = convertToDto( clienteRepository.save(entity) );
			return responseDTO;
			
		} catch (Exception e) {
			// Trata exceções de banco de dados e encapsula a exceção original
			// Captura e lança uma exceção personalizada para ser tratada mais acima
			throw new RuntimeException("Erro ao salvar o usuário no banco de dados", e);
		}
	}
	
	
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	
	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	
	public void remover(Long id) {
		clienteRepository.deleteById(id);
	}
	
	
	// ModelMapper - Converte uma entidade Cliente em um ResponseDTO para ser usado como Response para o Front 
	public ClienteResponseDTO convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

	// ModelMapper - 
    public Cliente convertToEntity(ClienteRequestDTO clienteRequestDTO) {
        return modelMapper.map(clienteRequestDTO, Cliente.class);
    }
}
