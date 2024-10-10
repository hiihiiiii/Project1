package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.RoomTypeDTO;
import com.utc.dormitory_managing.service.RoomTypeService;

@CrossOrigin
@RestController
@RequestMapping("/roomType")
public class RoomTypeAPI {
	@Autowired
	private RoomTypeService RoomTypeService;

	private static final String ENTITY_NAME = "RoomType";

	@PostMapping("")
	public ResponseDTO<RoomTypeDTO> create(@RequestBody RoomTypeDTO RoomTypeDTO) throws URISyntaxException {
		RoomTypeService.create(RoomTypeDTO);
		return ResponseDTO.<RoomTypeDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomTypeDTO).build();

	}

	@GetMapping("/{id}")
	public ResponseDTO<RoomTypeDTO> get(@PathVariable(value = "id") String id) {
		return ResponseDTO.<RoomTypeDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomTypeService.get(id))
				.build();
	}
	@GetMapping("/getAll")
	public ResponseDTO<List<RoomTypeDTO>> getAll() {
		return ResponseDTO.<List<RoomTypeDTO>>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomTypeService.getAll())
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) throws URISyntaxException {
		if (id == null) {
			throw new BadRequestAlertException("Bad request: missing id", ENTITY_NAME, "missing_id");
		}
		RoomTypeService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}
	
	@PutMapping("")
	public ResponseDTO<RoomTypeDTO> update(@RequestBody RoomTypeDTO RoomTypeDTO) throws URISyntaxException {
		RoomTypeService.update(RoomTypeDTO);
		return ResponseDTO.<RoomTypeDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomTypeDTO).build();

	}
}
