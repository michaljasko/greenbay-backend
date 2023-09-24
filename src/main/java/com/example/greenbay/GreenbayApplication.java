package com.example.greenbay;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Change terms and license if needed
@OpenAPIDefinition(info = @Info(title = "greenBay API", version = "1.0", description = "API for the greenBay, an eBay clone.", termsOfService = "http://example.com/terms/", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")))

@SpringBootApplication
public class GreenbayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenbayApplication.class, args);
	}

}
