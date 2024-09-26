package com.utc.dormitory_managing.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import com.utc.dormitory_managing.dto.BillDTO;
import com.utc.dormitory_managing.entity.Bill;
import com.utc.dormitory_managing.repository.BillRepo;

public interface BillService {
	BillDTO create(BillDTO billDTO);
	BillDTO update(BillDTO billDTO);
	Boolean delete(String id);
	BillDTO get(String id);
	List<BillDTO> getAll();
}
@Service
class BillServiceImpl implements BillService {

	@Autowired
	private BillRepo BillRepo;
	
	@Override
	public BillDTO create(BillDTO BillDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Bill Bill = mapper.map(BillDTO, Bill.class);
			Bill.setBillId(UUID.randomUUID().toString());
			BillRepo.save(Bill);
			return BillDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public BillDTO update(BillDTO BillDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Bill> BillOptional = BillRepo.findById(BillDTO.getBillId());
			if(BillOptional.isEmpty()) throw new BadRequestAlertException("Not Found Bill", "Bill", "missing");
			Bill Bill = mapper.map(BillDTO, Bill.class);
			BillRepo.save(Bill);
			return BillDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public Boolean delete(String id) {
		try {
			Optional<Bill> BillOptional = BillRepo.findById(id);
			if(BillOptional.isEmpty()) return false;
			BillRepo.deleteById(id);
			return true;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public BillDTO get(String id) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Bill> BillOptional = BillRepo.findById(id);
			if(BillOptional.isEmpty()) throw new BadRequestAlertException("Not Found Bill", "Bill", "missing");
			return mapper.map(BillOptional.get(), BillDTO.class);
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
		
	}

	@Override
	public List<BillDTO> getAll() {
		
		try {
			ModelMapper mapper = new ModelMapper();
			List<Bill> Bills = BillRepo.findAll();
			return Bills.stream().map(s -> mapper.map(s, BillDTO.class))
					.collect(Collectors.toList());
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}
	
	
}