package com.timber.timberrestservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimberRestServiceApplicationTests {

	@Autowired
	private TreeController controller;


	// SANITY CHECK
	
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
