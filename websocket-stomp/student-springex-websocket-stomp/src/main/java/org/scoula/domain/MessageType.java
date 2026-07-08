package org.scoula.domain;

/**
 * MessageType
 *
 * 멀티 사용자 채팅에서 메시지의 성격을 구분하기 위한 enum입니다.
 * - ENTER  : 사용자가 채팅방에 입장했을 때
 * - CHAT   : 사용자가 일반 채팅 메시지를 보냈을 때
 * - LEAVE  : 사용자가 채팅방에서 나갔을 때
 * - SYSTEM : 서버가 공지성 메시지를 보낼 때
 */
public enum MessageType {
    ENTER, CHAT, LEAVE, SYSTEM
}
