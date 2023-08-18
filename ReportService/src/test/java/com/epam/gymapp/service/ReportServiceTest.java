package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.exception.ReportException;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Report;
import com.epam.gymapp.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

	@Mock
	ReportRepository reportRepository;

	@Mock
	KafkaProducer kafkaProducer;

	@InjectMocks
	private ReportServiceImpl reportService;

	Report report = new Report();
	ReportDto reportDto = new ReportDto("Sai", "sai", "sai", true, 34, null);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	 void testSaveReport() {

		when(reportRepository.save(any())).thenReturn(report);
		Report result = reportService.saveReport(reportDto);
		assertEquals(report, result);
	}

	@Test
	void testSaveNullTrainee() {
		when(reportRepository.save(any())).thenThrow(ReportException.class);
		
		assertThrows(ReportException.class, () -> reportService.saveReport(reportDto));

	}

	@Test
	void testgetReport() {
		when(reportRepository.findByTrainerUserName(anyString())).thenReturn(Optional.of(report));
		when(kafkaProducer.sendReportList(anyList())).thenReturn(List.of(report));
		List<Report> result = reportService.getReport("hello");
		assertEquals(List.of(report), result);
	}

	@Test
	void testNullView() {
		
		when(reportRepository.findByTrainerUserName(anyString())).thenThrow(NullPointerException.class);
		assertThrows(ReportException.class, () -> reportService.getReport("sai@gmail.com"));
	}

}
