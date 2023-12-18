package com.revature.RevPay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {
		"com.revature.RevPay.controllers",
		"com.revature.RevPay.services",
		"com.revature.RevPay.repositories"})
public class RevPayFullstackProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevPayFullstackProjectApplication.class, args);
	}

}
