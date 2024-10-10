package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.ContractDTO;
import com.utc.dormitory_managing.service.ContractService;

@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractAPI {
	@Autowired
	private ContractService ContractService;

	private static final String ENTITY_NAME = "Contract";

	@PostMapping("")
	public ResponseDTO<ContractDTO> create(@RequestBody ContractDTO ContractDTO) throws URISyntaxException {
		ContractService.create(ContractDTO);
		return ResponseDTO.<ContractDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(ContractDTO).build();

	}

	@GetMapping("/{id}")
	public ResponseDTO<ContractDTO> get(@PathVariable(value = "id") String id) {
		return ResponseDTO.<ContractDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(ContractService.get(id))
				.build();
	}
	@GetMapping("/getAll")
	public ResponseDTO<List<ContractDTO>> getAll() {
		return ResponseDTO.<List<ContractDTO>>builder().code(String.valueOf(HttpStatus.OK.value())).data(ContractService.getAll())
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) throws URISyntaxException {
		if (id == null) {
			throw new BadRequestAlertException("Bad request: missing id", ENTITY_NAME, "missing_id");
		}
		ContractService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}
	
	@PutMapping("")
	public ResponseDTO<ContractDTO> update(@RequestBody ContractDTO ContractDTO) throws URISyntaxException {
		ContractService.update(ContractDTO);
		return ResponseDTO.<ContractDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(ContractDTO).build();

	}
}
