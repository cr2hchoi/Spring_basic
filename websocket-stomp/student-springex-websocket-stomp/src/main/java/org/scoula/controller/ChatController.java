package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.domain.ChatMessage;
import org.scoula.service.ChatRoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * ChatController - 학생용 TODO
 *
 * 멀티 사용자 채팅의 STOMP 메시지를 처리합니다.
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.join")
    public void join(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // TODO 1: sessionId를 얻으세요.
        String sessionId = headerAccessor.getSessionId();

        // TODO 2: ChatRoomService.enter()를 호출하세요.
        ChatMessage enterMessage = chatRoomService.enter(sessionId, message.getRoomId(), message.getSender());

        // TODO 3: /topic/rooms/{roomId}/messages 로 입장 메시지를 발행하세요.
        String roomId = enterMessage.getRoomId();
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/messages", enterMessage);

        // TODO 4: /topic/rooms/{roomId}/users 로 접속자 목록을 발행하세요.
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/users", chatRoomService.getUserListMessage(roomId));
    }

    @MessageMapping("/chat.send")
    public void send(ChatMessage message) {
        // TODO 5: 일반 채팅 메시지를 생성하고 방별 topic으로 발행하세요.
        ChatMessage chatMessage = chatRoomService.createChatMessage(message);
        messagingTemplate.convertAndSend("/topic/rooms/" + chatMessage.getRoomId() + "/messages", chatMessage);
    }

    @MessageMapping("/chat.leave")
    public void leave(SimpMessageHeaderAccessor headerAccessor) {
        // TODO 6: 명시적 퇴장 메시지를 처리하세요.
        publishLeaveMessage(headerAccessor.getSessionId());
    }

    public void publishLeaveMessage(String sessionId) {
        // TODO 7: sessionId 기준으로 퇴장 처리 후 메시지/사용자 목록을 발행하세요.
        ChatMessage leaveMessage = chatRoomService.leave(sessionId);
        if (leaveMessage == null) {
            return;
        }
        String roomId = leaveMessage.getRoomId();
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/messages", leaveMessage);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/users", chatRoomService.getUserListMessage(roomId));
    }
}
