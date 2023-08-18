package com.epam.gymapp.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.model.Report;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "reportservice", fallback = ReportServiceImpl.class)
//@LoadBalancerClient(name = "reportservice", configuration = ReportServiceImpl.class)
public interface ReportService {
	@PostMapping("/gymapp/reportservice/save")
	public ResponseEntity<Report> create(@RequestBody ReportDto report);

	@GetMapping("/gymapp/reportservice/view")
	public ResponseEntity<List<Report>> view(@RequestParam String trainerUserName);
}
