package org.scoula.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * [학생용 TODO] WebSocketConfig
 *
 * 목표:
 * - Spring WebSocket Message Broker를 활성화합니다.
 * - 클라이언트가 /chat-app 으로 WebSocket 연결을 맺을 수 있도록 endpoint를 등록합니다.
 * - /app prefix로 들어온 메시지를 @MessageMapping 메서드가 처리하도록 설정합니다.
 * - /topic prefix를 구독한 클라이언트에게 메시지가 전달되도록 Simple Broker를 설정합니다.
 */
@Configuration
@EnableWebSocketMessageBroker // TODO 1: WebSocket Message Broker 활성화 어노테이션의 의미를 README에서 정리하세요.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // TODO 2: 구독자가 사용할 topic prefix를 설정하세요.
        // 힌트: config.enableSimpleBroker("/topic");

        // TODO 3: 클라이언트가 서버로 메시지를 발행할 때 사용할 prefix를 설정하세요.
        // 힌트: config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO 4: WebSocket 연결 endpoint를 등록하세요.
        // 힌트: registry.addEndpoint("/chat-app").setAllowedOriginPatterns("*");
    }
}
