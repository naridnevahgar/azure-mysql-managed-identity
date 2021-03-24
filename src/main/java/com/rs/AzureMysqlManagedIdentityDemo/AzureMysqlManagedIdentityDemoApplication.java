package com.rs.AzureMysqlManagedIdentityDemo;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@SpringBootApplication
public class AzureMysqlManagedIdentityDemoApplication {

	@Autowired private ProductRepo productRepo;

	public static void main(String[] args) {
		SpringApplication.run(AzureMysqlManagedIdentityDemoApplication.class, args);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProduct(@PathVariable("productId") int productId) {
		Optional<Product> searchResult = productRepo.findById(productId);
		return searchResult.isPresent()?
				ResponseEntity.ok(searchResult.get())
				: ResponseEntity.notFound().build();
	}

}
