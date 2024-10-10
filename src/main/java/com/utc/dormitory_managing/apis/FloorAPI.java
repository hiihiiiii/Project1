package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.FloorDTO;
import com.utc.dormitory_managing.service.FloorService;

@CrossOrigin
@RestController
@RequestMapping("/floor")
public class FloorAPI {
	@Autowired
	private FloorService FloorService;

	private static final String ENTITY_NAME = "Floor";

	@PostMapping("")
	public ResponseDTO<FloorDTO> create(@RequestBody FloorDTO FloorDTO) throws URISyntaxException {
		FloorService.create(FloorDTO);
		return ResponseDTO.<FloorDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(FloorDTO).build();

	}

	@GetMapping("/{id}")
	public ResponseDTO<FloorDTO> get(@PathVariable(value = "id") String id) {
		return ResponseDTO.<FloorDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(FloorService.get(id))
				.build();
	}
	@GetMapping("/getAll")
	public ResponseDTO<List<FloorDTO>> getAll() {
		return ResponseDTO.<List<FloorDTO>>builder().code(String.valueOf(HttpStatus.OK.value())).data(FloorService.getAll())
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) throws URISyntaxException {
		if (id == null) {
			throw new BadRequestAlertException("Bad request: missing id", ENTITY_NAME, "missing_id");
		}
		FloorService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}
	
	@PutMapping("")
	public ResponseDTO<FloorDTO> update(@RequestBody FloorDTO FloorDTO) throws URISyntaxException {
		FloorService.update(FloorDTO);
		return ResponseDTO.<FloorDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(FloorDTO).build();

	}
}
