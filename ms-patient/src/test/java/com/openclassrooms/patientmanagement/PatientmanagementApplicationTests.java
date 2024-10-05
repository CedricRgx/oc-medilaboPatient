package com.openclassrooms.patientmanagement;

import com.openclassrooms.patientmanagement.service.impl.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class PatientmanagementApplicationTests {

	@MockBean
	private PatientService patientService;

	@Test
	void contextLoads() {
		assertNotNull(patientService);
	}

}
