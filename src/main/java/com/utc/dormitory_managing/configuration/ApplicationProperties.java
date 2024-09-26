package com.utc.dormitory_managing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class ApplicationProperties {
	@Value("${app.expiredTime}")
	private String expiredTime;
	
}