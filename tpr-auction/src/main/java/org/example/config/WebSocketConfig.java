package org.example.config;

import org.example.service.BidService;
import org.example.webSocketHandler.BidHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final BidService bidService;

    public WebSocketConfig(BidService bidService) {
        this.bidService = bidService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(bidHandler(), "/bid").setAllowedOrigins("*");
    }

    @Bean
    public BidHandler bidHandler() {
        return new BidHandler(bidService); // Внедрение bidService в хендлер
    }
}
