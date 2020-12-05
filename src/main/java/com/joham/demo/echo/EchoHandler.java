package com.joham.demo.echo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Component
public class EchoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive()
                        .map(msg -> webSocketSession.textMessage(
                                "服务端返回：小明， " + msg.getPayloadAsText())));
    }
}
