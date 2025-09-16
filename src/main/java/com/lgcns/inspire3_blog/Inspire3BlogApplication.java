package com.lgcns.inspire3_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableJpaAuditing
public class Inspire3BlogApplication {

    public static void main(String[] args) {

        Dotenv env = Dotenv.configure().ignoreIfMissing().load();
		env.entries().forEach(entry -> 
			System.setProperty(entry.getKey(), entry.getValue())
		);

        SpringApplication.run(Inspire3BlogApplication.class, args);
    }
}