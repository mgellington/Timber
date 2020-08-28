package com.timber.timberrestservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimberRestServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TimberRestServiceApplication.class, args);
		// XMLService xmlService = new XMLService();
        // List<Category> categories = xmlService.parseCategory();
        // // List<String> categoriesToString = new ArrayList<String>();

        // MemberFinder mf = new MemberFinder(categories.get(0).toString());
		// List<String> members = mf.getMembers();
		// System.out.println(categories.get(0));
		// System.out.println(members.size());


	}

}
