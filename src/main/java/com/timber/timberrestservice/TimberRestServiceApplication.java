package com.timber.timberrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class TimberRestServiceApplication implements CommandLineRunner {

	private XMLService xmlService;

	public TimberRestServiceApplication(XMLService xmlService) {
		this.xmlService = xmlService;
	}

	public static void main(String[] args) {

		// SpringApplication app = new SpringApplication(TimberRestServiceApplication.class);
		// app.setBannerMode(Banner.Mode.OFF);
		// app.run(args);

		SpringApplication.run(TimberRestServiceApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {

        // load course from XMLService
        List<Category> categories = xmlService.parseCategory();

		// print course details
		for (Category category : categories) {
			System.out.println(category);
		}
        
    }


}
