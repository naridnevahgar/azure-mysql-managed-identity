package com.rs.AzureMysqlManagedIdentityDemo;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DbConfig {

    private DataSource dataSource;

    @Bean
    @Primary
    public DataSource dataSource() throws IOException {
        if (dataSource == null) {

            ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().clientId("").build();
            TokenRequestContext tokenRequestContext = new TokenRequestContext();
            tokenRequestContext.addScopes("https://ossrdbms-aad.database.windows.net/.default");

            String accessToken = managedIdentityCredential.getToken(tokenRequestContext).block().getToken();

            HikariConfig config = new HikariConfig();
            config.setUsername("vmuser@idmgmt-pot-db.mysql.database.azure.com");
            config.setJdbcUrl("jdbc:mysql://idmgmt-pot-db.mysql.database.azure.com:3306/products?useSSL=true&requireSSL=false");
            config.setPassword(accessToken);

            HikariDataSource hikariDataSource = new HikariDataSource(config);
            dataSource = hikariDataSource;
        }

        return dataSource;
    }
}
