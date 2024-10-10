package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.RoomDTO;
import com.utc.dormitory_managing.service.RoomService;

@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomAPI {
	@Autowired
	private RoomService RoomService;

	private static final String ENTITY_NAME = "Room";

	@PostMapping("")
	public ResponseDTO<RoomDTO> create(@RequestBody RoomDTO RoomDTO) throws URISyntaxException {
		RoomService.create(RoomDTO);
		return ResponseDTO.<RoomDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomDTO).build();

	}

	@GetMapping("/{id}")
	public ResponseDTO<RoomDTO> get(@PathVariable(value = "id") String id) {
		return ResponseDTO.<RoomDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomService.get(id))
				.build();
	}
	@GetMapping("/getAll")
	public ResponseDTO<List<RoomDTO>> getAll() {
		return ResponseDTO.<List<RoomDTO>>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomService.getAll())
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) throws URISyntaxException {
		if (id == null) {
			throw new BadRequestAlertException("Bad request: missing id", ENTITY_NAME, "missing_id");
		}
		RoomService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}
	
	@PutMapping("")
	public ResponseDTO<RoomDTO> update(@RequestBody RoomDTO RoomDTO) throws URISyntaxException {
		RoomService.update(RoomDTO);
		return ResponseDTO.<RoomDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(RoomDTO).build();

	}
}
