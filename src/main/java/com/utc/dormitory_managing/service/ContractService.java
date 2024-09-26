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
import com.utc.dormitory_managing.dto.ContractDTO;
import com.utc.dormitory_managing.entity.Contract;
import com.utc.dormitory_managing.repository.ContractRepo;

public interface ContractService {
	ContractDTO create(ContractDTO contractDTO);
	ContractDTO update(ContractDTO contractDTO);
	Boolean delete(String id);
	ContractDTO get(String id);
	List<ContractDTO> getAll();
}
@Service
class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractRepo ContractRepo;
	
	@Override
	public ContractDTO create(ContractDTO ContractDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Contract Contract = mapper.map(ContractDTO, Contract.class);
			Contract.setContractId(UUID.randomUUID().toString());
			ContractRepo.save(Contract);
			return ContractDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public ContractDTO update(ContractDTO ContractDTO) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Contract> ContractOptional = ContractRepo.findById(ContractDTO.getContractId());
			if(ContractOptional.isEmpty()) throw new BadRequestAlertException("Not Found Contract", "Contract", "missing");
			Contract Contract = mapper.map(ContractDTO, Contract.class);
			ContractRepo.save(Contract);
			return ContractDTO;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public Boolean delete(String id) {
		try {
			Optional<Contract> ContractOptional = ContractRepo.findById(id);
			if(ContractOptional.isEmpty()) return false;
			ContractRepo.deleteById(id);
			return true;
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public ContractDTO get(String id) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<Contract> ContractOptional = ContractRepo.findById(id);
			if(ContractOptional.isEmpty()) throw new BadRequestAlertException("Not Found Contract", "Contract", "missing");
			return mapper.map(ContractOptional.get(), ContractDTO.class);
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
		
	}

	@Override
	public List<ContractDTO> getAll() {
		
		try {
			ModelMapper mapper = new ModelMapper();
			List<Contract> Contracts = ContractRepo.findAll();
			return Contracts.stream().map(s -> mapper.map(s, ContractDTO.class))
					.collect(Collectors.toList());
		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}
	}
