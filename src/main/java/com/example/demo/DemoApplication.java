package com.example.demo; // tu paquete raíz

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication { // nombre de la clase principal

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args); // arranca Spring Boot
    }
}
