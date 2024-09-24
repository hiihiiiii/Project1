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
import com.utc.dormitory_managing.dto.StudentDTO;
import com.utc.dormitory_managing.entity.Student;
import com.utc.dormitory_managing.entity.User;
import com.utc.dormitory_managing.repository.StudentRepo;

public interface StudentService {
	StudentDTO create(StudentDTO studentDTO);
	StudentDTO update(StudentDTO studentDTO);
	Boolean delete(String id);
	StudentDTO get(String id);
	List<StudentDTO> getAll();
}
@Service
class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Override
	public StudentDTO create(StudentDTO studentDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Student> studentOptional = studentRepo.findById(studentDTO.getStudentId());
			if(studentOptional.isEmpty()) throw new BadRequestAlertException("Not Found Student", "student", "missing");
			Student student = mapper.map(studentDTO, Student.class);
			User user = studentOptional.get().getUser();
			student.setUser(user);
			studentRepo.save(student);
			return studentDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public StudentDTO update(StudentDTO studentDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Student> studentOptional = studentRepo.findById(studentDTO.getStudentId());
			if(studentOptional.isEmpty()) throw new BadRequestAlertException("Not Found Student", "student", "missing");
			Student student = mapper.map(studentDTO, Student.class);
			User user = studentOptional.get().getUser();
			student.setUser(user);
			studentRepo.save(student);
			return studentDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public Boolean delete(String id) {
		try {
			Optional<Student> studentOptional = studentRepo.findById(id);
			if(studentOptional.isEmpty()) return false;
			studentRepo.deleteById(id);
			return true;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public StudentDTO get(String id) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Student> studentOptional = studentRepo.findById(id);
			if(studentOptional.isEmpty()) throw new BadRequestAlertException("Not Found Student", "student", "missing");
			return mapper.map(studentOptional.get(), StudentDTO.class);
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
		
	}

	@Override
	public List<StudentDTO> getAll() {
		
		try {
			ModelMapper mapper = new ModelMapper();
			List<Student> students = studentRepo.findAll();
			return students.stream().map(s -> mapper.map(s, StudentDTO.class))
					.collect(Collectors.toList());
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

}