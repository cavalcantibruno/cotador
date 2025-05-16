package com.creditas.cotador;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
public class CotadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CotadorApplication.class, args);
	}

	@Value("${app.timezone}")
	private String timezone;

	@PostConstruct
	void started() { TimeZone.setDefault(TimeZone.getTimeZone(timezone)); }

}
