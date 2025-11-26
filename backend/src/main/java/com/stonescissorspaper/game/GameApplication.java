package com.stonescissorspaper.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
        log.info("Game Application started successfully!");
	}

}
