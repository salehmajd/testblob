package com.studi.testblob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.storage.blob.*;

@Configuration
public class AzureStorageBlobClient {

    @Value("${azure.storage.ConnectionString}")
    private String connectionString;
    @Value("${azure.storage.container.name}")
    private String containerName;

    @Bean
    public BlobClientBuilder getClient() {
        BlobClientBuilder client = new BlobClientBuilder();
        client.connectionString(connectionString);
        client.containerName(containerName);
        return client;
    }

}