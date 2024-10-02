package com.utc.dormitory_managing.apis;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utc.dormitory_managing.dto.BookingDTO;
import com.utc.dormitory_managing.dto.BuildingDTO;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.dto.RoomTypeDTO;
import com.utc.dormitory_managing.dto.StudentDTO;
import com.utc.dormitory_managing.entity.Student;
import com.utc.dormitory_managing.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingAPI {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("")
	public ResponseDTO<String> checkIn(@RequestBody @Valid BookingDTO bookingDTO) throws URISyntaxException {
		String e= bookingService.checkIn(bookingDTO.getStudentDTO(), bookingDTO.getRoomTypeDTO());
		return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(e).build();

	}
}
