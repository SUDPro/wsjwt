package ru.itis.websockets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.websockets.forms.MessageDto;
import ru.itis.websockets.services.MessageService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesWebSocketHandler extends TextWebSocketHandler {


    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MessageService messageService;

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageAsString = (String) message.getPayload();
        MessageDto messageDto = objectMapper.readValue(messageAsString, MessageDto.class);
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(messageDto.getFrom()).getBody();
        messageDto.setFrom((String)body.get("name"));
        messageDto.setFromId(new Long(body.get("id").toString()));
        if (messageDto.getText().equals("")) {
            sessions.put(messageDto.getFrom(), session);
        } else {
           messageService.save(messageDto);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (WebSocketSession currentSession : sessions.values()) {
            currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
        }
    }
}
