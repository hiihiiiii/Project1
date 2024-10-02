package com.utc.dormitory_managing.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class ApplicationProperties {
	@Value("${app.expiredTime}")
	private String expiredTime;
	private List<String> STATUS_CONTRACT = Arrays.asList("WAITING", "ACTIVE", "SUSPEND");

	public static enum StatusContractRef {
		WAITING, ACTIVE, SUSPEND
	}
}