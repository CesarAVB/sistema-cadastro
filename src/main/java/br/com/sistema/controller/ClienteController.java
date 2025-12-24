package br.com.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema.dto.ClienteRequestDTO;
import br.com.sistema.dto.ClienteResponseDTO;
import br.com.sistema.model.Cliente;
import br.com.sistema.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<String> salvar( @RequestBody ClienteRequestDTO cliente) {
		ClienteResponseDTO clienteResponseDTO = clienteService.save(cliente);
//		return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO); //Utilizar o retorno ResponseEntity<ClienteResponseDTO> no método, se quiser retornar os dados
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente adicionado: " + clienteResponseDTO.getNome());
	}
	
	
	@GetMapping
	public ResponseEntity< List<Cliente> > listarTodos() {
		List<Cliente> clientes = clienteService.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
}
