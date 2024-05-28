package org.example.config;

import feign.Client;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig {

    @Bean
    public Client client() {
        return new OkHttpClient();
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter()));
    }

    @Bean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new SpringDecoder(() -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter())));
    }
}
