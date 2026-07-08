package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * UserListMessage
 *
 * 특정 채팅방의 현재 접속자 목록을 클라이언트에게 전달하기 위한 DTO입니다.
 * 클라이언트는 /topic/rooms/{roomId}/users 를 구독하고 있다가 이 객체를 수신하면
 * 접속자 목록 UI를 다시 그립니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListMessage {
    private String roomId;
    @Builder.Default
    private List<String> users = new ArrayList<>();
    private int count;
}
