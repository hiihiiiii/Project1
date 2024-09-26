package com.utc.dormitory_managing.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class SearchDTO {
	public static final int MAX_20 = 20;
	public static final int MAX_200 = 200;
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	@Min(value = 0)
	private int page;

	@Min(value = 1)
	@Max(value = MAX_200)
	@Builder.Default
	private int size = MAX_200;

	@Builder.Default
	private String value = "%%";

	private List<OrderBy> orders;

//	@Builder.Default
//	private Map<String, String> filterBys = new HashMap<>();
	
	@Data
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OrderBy {
		@Builder.Default
		private String order = ASC;// asc, desc
		private String property;
	}
}
