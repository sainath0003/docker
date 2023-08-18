package com.epam.gymapp.service;

import java.util.List;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.model.Report;

public interface ReportService {
	public Report saveReport(ReportDto reportDto);

	public List<Report> getReport(String trainerUserName);
}
