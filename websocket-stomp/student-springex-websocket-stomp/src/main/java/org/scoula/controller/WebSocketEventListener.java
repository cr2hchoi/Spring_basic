package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * WebSocketEventListener - 학생용 TODO
 *
 * 브라우저 탭 종료나 네트워크 끊김 상황에서도 사용자 목록이 정리되도록
 * SessionDisconnectEvent를 처리합니다.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class WebSocketEventListener {

    private final ChatController chatController;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // TODO: event.getSessionId()로 sessionId를 얻고 퇴장 메시지를 발행하세요.
        String sessionId = event.getSessionId();
        chatController.publishLeaveMessage(sessionId);
    }
}
