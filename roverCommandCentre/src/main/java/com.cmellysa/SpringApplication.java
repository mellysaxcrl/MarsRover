package com.cmellysa;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class);
        if (args.length % 2 != 0) {
            System.out.println("Invalid input! Number of rovers and instructions don't match!");
        } else {
            int num_rovers = args.length / 2;

        }
    }
}
