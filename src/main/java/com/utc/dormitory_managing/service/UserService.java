package com.utc.dormitory_managing.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.utc.dormitory_managing.apis.error.BadRequestAlertException;
import com.utc.dormitory_managing.configuration.ApplicationProperties;
import com.utc.dormitory_managing.dto.UserDTO;
import com.utc.dormitory_managing.entity.User;
import com.utc.dormitory_managing.repository.UserRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface UserService {
	UserDTO create(UserDTO userDTO);

	UserDTO findByName(String name);

	Boolean delete(String id);

	Boolean deleteAll(List<String> ids);
	
	List<UserDTO> getAll();

	UserDTO get(String id);

	UserDTO updatePassword(UserDTO userDTO);

//	List<UserDTO> search(SearchDTO searchDTO);
	
	UserDTO update(UserDTO userDTO);
	
}

@Service
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ApplicationProperties props;


	@Override
	@Transactional
	public UserDTO create(UserDTO userDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			// creatte user
			User user = mapper.map(userDTO, User.class);
			
			if(userRepo.findByUsername(userDTO.getUsername()).isPresent()) throw new BadRequestAlertException("user is available", "user", "missing");
			
			String user_id = UUID.randomUUID().toString().replaceAll("-", "");
			user.setUserId(user_id);
			user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			if(userDTO.getExpired()<=0 || user.getExpired()==null) user.setExpired((long)props.getExpiredTime()*12);
			else {
				user.setExpired((long)props.getExpiredTime()* user.getExpired());
			}
			// commit save
			userRepo.save(user);
			return mapper.map(user, UserDTO.class);

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public UserDTO findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional
	public Boolean deleteAll(List<String> ids) {
		if(ids.isEmpty()) return false;
		userRepo.deleteAllById(ids);
		return true;
	}

	@Override
	@Transactional
	public UserDTO get(String id) {
		try {
			User user = userRepo.findByUserId(id).orElseThrow(NoResultException::new);
			UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
			return userDTO;

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}

	}

	@Override
	@Transactional
	public UserDTO updatePassword(UserDTO userDTO) {
		try {
			User user = userRepo.findByUserId(userDTO.getUserId()).orElseThrow(NoResultException::new);
			user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			userRepo.save(user);
			UserDTO userResponse = new ModelMapper().map(user, UserDTO.class);
			return userResponse;

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}

	}

//	@Override
//	public List<UserDTO> search(SearchDTO searchDTO) {
//		try {
//			List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList)
//					.stream().map(order -> {
//						if (order.getOrder().equals(SearchDTO.ASC))
//							return Sort.Order.asc(order.getProperty());
//
//						return Sort.Order.desc(order.getProperty());
//					}).collect(Collectors.toList());
//
//			Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));
//
//			Page<User> page = userRepo.search(searchDTO.getValue(), pageable);
//
//			List<UserResponse> userDTOList = new ArrayList<>();
//
//			for (User user : page.getContent()) {
//				UserResponse userResponse = new ModelMapper().map(user, UserResponse.class);
//				userDTOList.add(userResponse);
//			}
//
//			return userDTOList;
//		} catch (ResourceAccessException e) {
//			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
//		} catch (HttpServerErrorException | HttpClientErrorException e) {
//			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
//		}
//	}

	@Override
	public List<UserDTO> getAll() {
		ModelMapper mapper = new ModelMapper();
		List<User> users  = userRepo.findAll();
		return users
				  .stream()
				  .map(user -> mapper.map(user, UserDTO.class))
				  .collect(Collectors.toList());
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		try {
			User user = userRepo.findByUserId(userDTO.getUserId()).orElseThrow(NoResultException::new);
			ModelMapper model = new ModelMapper();
			user = model.map(userDTO, User.class);
			user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			userRepo.save(user);
			return userDTO;

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public Boolean delete(String id) {
		try {
		Optional<User> userOptional = userRepo.findById(id);
		if(userOptional.isEmpty()) return false;
		userRepo.deleteById(id);
		return true;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}
	
	

}
