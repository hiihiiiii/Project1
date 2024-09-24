package com.utc.dormitory_managing.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.dto.StaffDTO;
import com.utc.dormitory_managing.entity.Staff;
import com.utc.dormitory_managing.entity.User;
import com.utc.dormitory_managing.repository.StaffRepo;

public interface StaffService {
	StaffDTO create(StaffDTO StaffDTO);
	StaffDTO update(StaffDTO StaffDTO);
	Boolean delete(String id);
	StaffDTO get(String id);
	List<StaffDTO> getAll();
}
@Service
class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffRepo StaffRepo;
	
	@Override
	public StaffDTO create(StaffDTO staffDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Staff> StaffOptional = StaffRepo.findById(staffDTO.getStaffId());
			if(StaffOptional.isEmpty()) throw new BadRequestAlertException("Not Found Staff", "Staff", "missing");
			Staff Staff = mapper.map(staffDTO, Staff.class);
			User user = StaffOptional.get().getUser();
			Staff.setUser(user);
			StaffRepo.save(Staff);
			return staffDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public StaffDTO update(StaffDTO StaffDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Staff> StaffOptional = StaffRepo.findById(StaffDTO.getStaffId());
			if(StaffOptional.isEmpty()) throw new BadRequestAlertException("Not Found Staff", "Staff", "missing");
			Staff Staff = mapper.map(StaffDTO, Staff.class);
			User user = StaffOptional.get().getUser();
			Staff.setUser(user);
			StaffRepo.save(Staff);
			return StaffDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public Boolean delete(String id) {
		try {
			Optional<Staff> StaffOptional = StaffRepo.findById(id);
			if(StaffOptional.isEmpty()) return false;
			StaffRepo.deleteById(id);
			return true;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public StaffDTO get(String id) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Staff> StaffOptional = StaffRepo.findById(id);
			if(StaffOptional.isEmpty()) throw new BadRequestAlertException("Not Found Staff", "Staff", "missing");
			return mapper.map(StaffOptional.get(), StaffDTO.class);
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
		
	}

	@Override
	public List<StaffDTO> getAll() {
		
		try {
			ModelMapper mapper = new ModelMapper();
			List<Staff> Staffs = StaffRepo.findAll();
			return Staffs.stream().map(s -> mapper.map(s, StaffDTO.class))
					.collect(Collectors.toList());
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

}
