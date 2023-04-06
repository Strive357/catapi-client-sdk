package com.catapi.sdkconfig;

import com.catapi.sdkconfig.client.CatApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@Data
@ConfigurationProperties("catapi.client")
public class CatApiClientConfig {

    private String accessKey;

    private String secretKey;


    @Bean
    public CatApiClient catApiClient(){
        return new CatApiClient(accessKey,secretKey);
    }
}
