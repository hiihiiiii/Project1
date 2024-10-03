package com.utc.dormitory_managing.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.configuration.ApplicationProperties;
import com.utc.dormitory_managing.configuration.ApplicationProperties.StatusContractRef;
import com.utc.dormitory_managing.dto.ContractDTO;
import com.utc.dormitory_managing.dto.RoomTypeDTO;
import com.utc.dormitory_managing.dto.StudentDTO;
import com.utc.dormitory_managing.entity.Contract;
import com.utc.dormitory_managing.entity.Room;
import com.utc.dormitory_managing.entity.RoomType;
import com.utc.dormitory_managing.entity.Student;
import com.utc.dormitory_managing.repository.ContractRepo;
import com.utc.dormitory_managing.repository.RoomRepo;
import com.utc.dormitory_managing.repository.RoomTypeRepo;
import com.utc.dormitory_managing.repository.StudentRepo;
import com.utc.dormitory_managing.utils.Utils;

import jakarta.persistence.NoResultException;


public interface BookingService {

	//sinh viên đăng kí phòng
	//sau khi sinh viên đăng kí phòng sẽ có 1 1 hợp đồng tạm thời
	//được tạo ra và lưu trữ dưới dạng hợp đồng tạm thời 
	//nếu sau 1 ngày không đóng cọ và tiền phòng hợp đồng sẽ bị xóa
	Boolean checkValidRoom(RoomTypeDTO roomTypeDTO);
	
	String checkIn(StudentDTO studentDTO, RoomTypeDTO roomTypeDTO);
	
	void RoomSoft(RoomTypeDTO roomTypeDTO);
	
}
@Service
class BookingServiceImpl implements BookingService {

	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private RoomTypeRepo roomTypeRepo;
	
	@Autowired
	private ContractRepo contractRepo;
	
	@Autowired
	private ApplicationProperties props;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private ContractService contractService;
	
	@Override
	public Boolean checkValidRoom(RoomTypeDTO roomTypeDTO) {
		try {
			//kiem tra xem con phong trong hay khong bang cach check tat ca cac hop dong hien tai con dang ki trong phong
			
			Optional<RoomType> roomOptional = roomTypeRepo.findById(roomTypeDTO.getRoomTypeId());
			if(roomOptional.isEmpty()) throw new BadRequestAlertException("Not Found RoomType", "RoomType", "missingId");
			//moi roomtype co bao nhieu phong
			Long roomTypeNumber = roomRepo.getRoomNumber(roomTypeDTO.getRoomTypeId()) * roomOptional.get().getRoomTypeNumber();
			
			Long contractNumber = contractRepo.getContractNumber(roomTypeDTO.getRoomTypeId(), StatusContractRef.SUSPEND.toString());
			
			if(roomTypeNumber > contractNumber) return true;
			return false;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public String checkIn(StudentDTO studentDTO, RoomTypeDTO roomTypeDTO) {
		try {
			//kiem tra con phong trong khong
			ModelMapper mapper = new ModelMapper();
			Student student = studentRepo.findById(studentDTO.getStudentId()).orElseThrow(NoResultException::new);
			if(!checkValidRoom(roomTypeDTO)) return new String("RoomType is Full");
			ContractDTO contractDTO = new ContractDTO();
			contractDTO.setStudent(student);
			contractDTO.setStartDate(new Date());
			contractDTO.setEndDate(Utils.setTimeContract());
			RoomType roomType = roomTypeRepo.findById(roomTypeDTO.getRoomTypeId()).orElseThrow(NoResultException::new);
			contractDTO.setRoomType(roomType);
			contractDTO.setContractStatus(StatusContractRef.WAITING.toString());
			if(student.getStudentPriority()!= 0) {
				contractDTO.setReduceCost((long)0);
				contractDTO.setContractRent(roomType.getRoomTypeDeposit()+ roomType.getRoomTypePrice()- contractDTO.getReduceCost());
			}else {
				contractDTO.setReduceCost(roomType.getRoomTypeDeposit());
				contractDTO.setContractRent(roomType.getRoomTypeDeposit()+ roomType.getRoomTypePrice()- contractDTO.getReduceCost());
			}
			contractService.create(contractDTO);
			return new String("Successfull!!");
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public void RoomSoft(RoomTypeDTO roomTypeDTO) {
		try {
			if(!roomTypeRepo.existsById(roomTypeDTO.getRoomTypeId())) throw new BadRequestAlertException("Not Found RoomTypeId", "RoomType", "Not Found");
			List<Contract> contracts = contractRepo.findContractByStatusAndRoomType(roomTypeDTO.getRoomTypeId(),StatusContractRef.ACTIVE.toString());
			List<Room> rooms = roomRepo.findByRoomType(roomTypeDTO.getRoomTypeId());
			if(rooms.size()==0) return;
			if(contracts.size()==0) return;
			// Tạo đối tượng Random
	        Random random = new Random();
			for (Room room : rooms) {
				int i = room.getRoomNumber();
				while(i< roomTypeDTO.getRoomNumber()|| contracts.size()>0) {
					int r = random.nextInt(contracts.size());
					room.getStudents().add(contracts.get(r).getStudent());
					contracts.remove(r);
					i++;
				}
				room.setRoomNumber(i);
				room.setRoomValid(false);
				roomRepo.save(room);
			}
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
		
	}
	
	
	
	
}