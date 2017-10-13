package org.prz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PraktykiApplication{

	public static void main(String[] args) {
		SpringApplication.run(PraktykiApplication.class, args);
	}
       
}
