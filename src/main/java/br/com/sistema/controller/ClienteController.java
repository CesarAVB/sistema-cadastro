package br.com.sistema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.sistema.dto.ClienteRequestDTO;
import br.com.sistema.dto.ClienteResponseDTO;
import br.com.sistema.dto.MetricaDashboard;
import br.com.sistema.model.Cliente;
import br.com.sistema.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "CPF/CNPJ já cadastrado")
    })
    public ResponseEntity<ClienteResponseDTO> salvar(
            @Parameter(description = "Dados do cliente a ser cadastrado")
            @RequestBody ClienteRequestDTO cliente) {
        ClienteResponseDTO clienteResponseDTO = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID do cliente")
            @PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "409", description = "CPF/CNPJ já cadastrado em outro cliente")
    })
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @Parameter(description = "ID do cliente")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do cliente")
            @RequestBody ClienteRequestDTO cliente) {
        ClienteResponseDTO clienteResponseDTO = clienteService.atualizar(id, cliente);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do cliente")
            @PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/metricas")
    @Operation(summary = "Obter métricas do dashboard", description = "Retorna estatísticas gerais dos clientes")
    @ApiResponse(responseCode = "200", description = "Métricas retornadas com sucesso")
    public ResponseEntity<MetricaDashboard> obterMetricas() {
        MetricaDashboard metricas = clienteService.calcularMetricas();
        return ResponseEntity.status(HttpStatus.OK).body(metricas);
    }

    @GetMapping("/recentes")
    @Operation(summary = "Obter clientes recentes", description = "Retorna os clientes cadastrados mais recentemente")
    @ApiResponse(responseCode = "200", description = "Clientes recentes retornados com sucesso")
    public ResponseEntity<List<Cliente>> obterClientesRecentes(
            @Parameter(description = "Quantidade de clientes a retornar")
            @RequestParam(defaultValue = "4") int limite) {
        List<Cliente> clientes = clienteService.obterRecentes(limite);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }
}
