package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [학생용 TODO] GreetingMessage
 *
 * 목표:
 * - 입장 메시지를 담는 DTO를 완성하세요.
 * - STOMP 메시지 Body의 JSON이 이 객체로 자동 변환됩니다.
 *
 * 예시 JSON:
 * {
 *   "name": "홍길동"
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingMessage {
    // TODO 1: 사용자 이름을 저장할 필드를 작성하세요.
    // 힌트: private String name;
}
