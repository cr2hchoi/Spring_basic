package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ChatMessage - 학생용 TODO
 *
 * 브라우저와 서버가 주고받는 채팅 메시지 DTO입니다.
 * TODO 주석을 보고 멀티 사용자 채팅에 필요한 필드를 완성하세요.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    // TODO 1: 채팅방 ID 필드 선언
    private String roomId;

    // TODO 2: 메시지 발신자 필드 선언
    private String sender;

    // TODO 3: 채팅 내용 필드 선언
    private String content;

    // TODO 4: ENTER, CHAT, LEAVE, SYSTEM을 구분하기 위한 MessageType 필드 선언
    private MessageType type;

    // TODO 5: 서버 처리 시간을 저장할 LocalDateTime 필드 선언
    private String sentAt;

    // TODO 6: 현재 방 접속자 수 필드 선언
    private int participantCount;
}
