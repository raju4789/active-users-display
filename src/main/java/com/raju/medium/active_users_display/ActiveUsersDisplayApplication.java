package com.raju.medium.active_users_display;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ActiveUsersDisplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveUsersDisplayApplication.class, args);
	}

}
