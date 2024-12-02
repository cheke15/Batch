package com.reporte.empleo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class EmpleoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmpleoApplication.class, args);
    }
}