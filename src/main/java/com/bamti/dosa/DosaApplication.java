package com.bamti.dosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration; // 추가
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration; // 추가

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class, // 하이버네이트 자동 설정 끄기
		JpaRepositoriesAutoConfiguration.class // JPA 리포지토리 자동 설정 끄기
})
public class DosaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DosaApplication.class, args);
	}

}