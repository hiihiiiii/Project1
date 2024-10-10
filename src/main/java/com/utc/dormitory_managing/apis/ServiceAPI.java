package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.ServiceDTO;
import com.utc.dormitory_managing.service.ServiceService;

@CrossOrigin
@RestController
@RequestMapping("/service")
public class ServiceAPI {
	@Autowired
	private ServiceService serviceService;
	

	private static final String ENTITY_NAME = "Service";
	
	@PostMapping("")
	public ResponseDTO<ServiceDTO> create(@RequestBody ServiceDTO serviceDTO) throws URISyntaxException {
		serviceService.create(serviceDTO);
		return ResponseDTO.<ServiceDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(serviceDTO).build();

	}

	@GetMapping("/{id}")
	public ResponseDTO<ServiceDTO> get(@PathVariable(value = "id") String id) {
		return ResponseDTO.<ServiceDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(serviceService.get(id))
				.build();
	}
	@GetMapping("/getAll")
	public ResponseDTO<List<ServiceDTO>> getAll() {
		return ResponseDTO.<List<ServiceDTO>>builder().code(String.valueOf(HttpStatus.OK.value())).data(serviceService.getAll())
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) throws URISyntaxException {
		if (id == null) {
			throw new BadRequestAlertException("Bad request: missing id", ENTITY_NAME, "missing_id");
		}
		serviceService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}
	
	@PutMapping("")
	public ResponseDTO<ServiceDTO> update(@RequestBody ServiceDTO serviceDTO) throws URISyntaxException {
		serviceService.update(serviceDTO);
		return ResponseDTO.<ServiceDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(serviceDTO).build();

	}
}
