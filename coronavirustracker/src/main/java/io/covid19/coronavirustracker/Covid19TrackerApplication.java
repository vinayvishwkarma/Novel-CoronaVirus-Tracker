package io.covid19.coronavirustracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"io.covid19.coronavirustracker.services","io.covid19.coronavirustracker.services","io.covid19.coronavirustracker.controller","io.covid19.coronavirustracker.model"})
@SpringBootApplication
@EnableScheduling
public class Covid19TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerApplication.class, args);
	}

}
