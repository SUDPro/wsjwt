package ru.itis.websockets.handlers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;
import ru.itis.websockets.configs.Access;

import java.util.Map;

@Component
public class AuthHandshakeHandler implements HandshakeHandler {

    private DefaultHandshakeHandler handshakeHandler = new DefaultHandshakeHandler();

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";

    @Override
    public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws HandshakeFailureException {
        ServletServerHttpRequest request = (ServletServerHttpRequest)serverHttpRequest;
        System.out.println(WebUtils.getCookie(request.getServletRequest(), "AUTH_WEB_SOCKETS"));
        String cookie = WebUtils.getCookie(request.getServletRequest(), "AUTH_WEB_SOCKETS").getValue();
        if (cookieIsCorrect(cookie)) {
            return handshakeHandler.doHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
        } else {
            System.out.println("Forbidden");
            serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }
    }

    public boolean cookieIsCorrect(String cookie) {
        System.out.println(cookie);
        try {
            Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(cookie).getBody();
//            System.out.println(body.get("access"));
            System.out.println(Access.ALLOWED.toString());
            return body.get("access").equals(Access.ALLOWED.toString());
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }
}
