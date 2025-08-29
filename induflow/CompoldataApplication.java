package com.compoldata.induflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class CompoldataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompoldataApplication.class, args);
	}

}
