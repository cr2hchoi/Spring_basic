package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.scoula.domain.ChatMessage;
import org.scoula.domain.MessageType;
import org.scoula.domain.UserListMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChatRoomService - 학생용 TODO
 *
 * 멀티 사용자 채팅방 상태를 메모리에서 관리하는 서비스입니다.
 */
@Service
@Log4j2
public class ChatRoomService {

    // TODO 1: roomId별 사용자 목록을 저장할 ConcurrentHashMap을 준비하세요.
    private final Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();

    // TODO 2: sessionId → roomId 매핑을 저장하세요.
    private final Map<String, String> sessionRoomMap = new ConcurrentHashMap<>();

    // TODO 3: sessionId → username 매핑을 저장하세요.
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public ChatMessage enter(String sessionId, String roomId, String sender) {
        // TODO 4: roomId와 sender를 정규화하세요.
        String normalizedRoomId = normalizeRoomId(roomId);
        String normalizedSender = normalizeSender(sender);

        // TODO 5: 방이 없으면 생성하고 사용자를 추가하세요.
        roomUsers.computeIfAbsent(normalizedRoomId, key -> Collections.synchronizedSet(new LinkedHashSet<>()))
                .add(normalizedSender);

        // TODO 6: 연결 종료 시 추적할 수 있도록 session 정보를 저장하세요.
        sessionRoomMap.put(sessionId, normalizedRoomId);
        sessionUserMap.put(sessionId, normalizedSender);

        // TODO 7: 입장 메시지를 생성해 반환하세요.
        return ChatMessage.builder()
                .roomId(normalizedRoomId)
                .sender(normalizedSender)
                .content(normalizedSender + "님이 입장했습니다.")
                .type(MessageType.ENTER)
                .sentAt(java.time.LocalDateTime.now().toString())
                .participantCount(getUserCount(normalizedRoomId))
                .build();
    }

    public ChatMessage createChatMessage(ChatMessage message) {
        // TODO 8: 일반 채팅 메시지를 표준 형식으로 보정하세요.
        String roomId = normalizeRoomId(message.getRoomId());
        String sender = normalizeSender(message.getSender());
        String content = message.getContent() == null ? "" : message.getContent().trim();

        return ChatMessage.builder()
                .roomId(roomId)
                .sender(sender)
                .content(content)
                .type(MessageType.CHAT)
                .sentAt(java.time.LocalDateTime.now().toString())
                .participantCount(getUserCount(roomId))
                .build();
    }

    public ChatMessage leave(String sessionId) {
        // TODO 9: sessionId로 roomId와 sender를 찾고 Map에서 제거하세요.
        String roomId = sessionRoomMap.remove(sessionId);
        String sender = sessionUserMap.remove(sessionId);

        if (roomId == null || sender == null) {
            return null;
        }

        // TODO 10: roomUsers에서 사용자를 제거하세요.
        Set<String> users = roomUsers.get(roomId);
        if (users != null) {
            users.remove(sender);
            if (users.isEmpty()) {
                roomUsers.remove(roomId);
            }
        }

        // TODO 11: 퇴장 메시지를 반환하세요.
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(sender)
                .content(sender + "님이 퇴장했습니다.")
                .type(MessageType.LEAVE)
                .sentAt(java.time.LocalDateTime.now().toString())
                .participantCount(getUserCount(roomId))
                .build();
    }

    public UserListMessage getUserListMessage(String roomId) {
        // TODO 12: 현재 방의 사용자 목록을 UserListMessage로 반환하세요.
        String normalizedRoomId = normalizeRoomId(roomId);
        List<String> users = new ArrayList<>(roomUsers.getOrDefault(normalizedRoomId, Collections.emptySet()));
        return UserListMessage.builder()
                .roomId(normalizedRoomId)
                .users(users)
                .count(users.size())
                .build();
    }

    private int getUserCount(String roomId) {
        return roomUsers.getOrDefault(roomId, Collections.emptySet()).size();
    }

    private String normalizeRoomId(String roomId) {
        return roomId == null || roomId.trim().isEmpty() ? "lobby" : roomId.trim();
    }

    private String normalizeSender(String sender) {
        return sender == null || sender.trim().isEmpty() ? "anonymous" : sender.trim();
    }
}
