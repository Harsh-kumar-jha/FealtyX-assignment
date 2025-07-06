package com.FealtyX_assignment.Student.config;

import com.FealtyX_assignment.Student.clients.IOllamaApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class OllamaConfig {
    
    @Value("${ollama.api.base-url}")
    private String ollamaBaseUrl;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
    }

    @Bean
    public Retrofit retrofit(ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(ollamaBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();
    }

    @Bean
    public IOllamaApi ollamaApi(Retrofit retrofit) {
        return retrofit.create(IOllamaApi.class);
    }
}
