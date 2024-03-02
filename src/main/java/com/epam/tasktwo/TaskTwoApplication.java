package com.epam.tasktwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTwoApplication.class, args);
	}

}
