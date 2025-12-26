package br.com.sistema.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import br.com.sistema.dto.ClienteRequestDTO;
import br.com.sistema.dto.ClienteResponseDTO;
import br.com.sistema.dto.MetricaDashboard;
import br.com.sistema.exceptions.DadosInvalidosException;
import br.com.sistema.model.Cliente;
import br.com.sistema.repository.ClienteRepository;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;

    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {
        Optional<Cliente> clienteRetornado = clienteRepository.findByCpfCnpj(clienteRequestDTO.getCpfCnpj());

        if (clienteRetornado.isPresent()) {
            throw new DadosInvalidosException("CPF/CNPJ já cadastrado no sistema.");
        }

        var entity = convertToEntity(clienteRequestDTO);

        try {
            var responseDTO = convertToDto(clienteRepository.save(entity));
            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o cliente no banco de dados", e);
        }
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isEmpty()) {
            throw new DadosInvalidosException("Cliente não encontrado.");
        }

        Optional<Cliente> clienteComMesmoCpfCnpj = clienteRepository.findByCpfCnpj(clienteRequestDTO.getCpfCnpj());
        if (clienteComMesmoCpfCnpj.isPresent() && !clienteComMesmoCpfCnpj.get().getId().equals(id)) {
            throw new DadosInvalidosException("CPF/CNPJ já cadastrado em outro cliente.");
        }

        var entity = convertToEntity(clienteRequestDTO);
        entity.setId(id);

        try {
            var responseDTO = convertToDto(clienteRepository.save(entity));
            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o cliente no banco de dados", e);
        }
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void remover(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw new DadosInvalidosException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    public MetricaDashboard calcularMetricas() {
        Long totalClientes = clienteRepository.count();
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDiaHoje = hoje.atStartOfDay();
        Long novosHoje = clienteRepository.countByDataCadastro(inicioDoDiaHoje); 
        Double mediaDiaria = totalClientes / 30.0;
        Long clientesAtivos = totalClientes / 2;
        Double variacao = 2.5;
        Double percentualAtivos = totalClientes > 0 ? (clientesAtivos * 100.0) / totalClientes : 0.0;

        return new MetricaDashboard(totalClientes, variacao, novosHoje, mediaDiaria, clientesAtivos, percentualAtivos);
    }

    // Usa findTop10ByOrderByIdDesc
    public List<Cliente> obterRecentes(int limite) {
        List<Cliente> clientes = clienteRepository.findTop10ByOrderByIdDesc();

        return clientes.stream().limit(limite).collect(Collectors.toList());
    }

    public ClienteResponseDTO convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    public Cliente convertToEntity(ClienteRequestDTO clienteRequestDTO) {
        return modelMapper.map(clienteRequestDTO, Cliente.class);
    }
}
