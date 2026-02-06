package com.bamti.dosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // DB 자동 설정 제외
public class DosaApplication {
	/**
	 * Bootstraps and starts the Spring Boot application for Dosa.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(DosaApplication.class, args);
	}
}