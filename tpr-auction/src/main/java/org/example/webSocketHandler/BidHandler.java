package org.example.webSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.web.request.BidRequest;
import org.example.service.BidService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class BidHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final BidService bidService;

    public BidHandler(BidService bidService) {
        this.bidService = bidService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> bidData = objectMapper.readValue(payload, Map.class);

        Long auctionId = Long.valueOf(bidData.get("auctionId").toString());

        BidRequest bidRequest = new BidRequest();
        Long clientId = Long.valueOf(bidData.get("clientId").toString());
        bidRequest.setClientId(clientId);
        bidRequest.setAmount(Float.valueOf(bidData.get("amount").toString()));

        try {
            bidService.placeBid(auctionId, bidRequest);

            Map<String, Object> responseMessage = Map.of(
                    "clientId", clientId,
                    "auctionId", auctionId,
                    "amount", bidRequest.getAmount()
            );

            String jsonResponse = objectMapper.writeValueAsString(responseMessage);

            for (WebSocketSession activeSession : sessions) {
                if (activeSession.isOpen()) {
                    activeSession.sendMessage(new TextMessage(jsonResponse));
                }
            }

        } catch (Exception e) {
            session.sendMessage(new TextMessage(STR."Error: \{e.getMessage()}"));
        }
    }
}
