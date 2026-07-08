# Spring WebSocket STOMP 멀티 사용자 메시지 서비스

실제 채팅 서비스처럼 여러 사용자가 동시에 접속하고, 채팅방별로 메시지를 분리하고, 접속자 목록을 관리

```text
/app/chat.join  -> /topic/rooms/{roomId}/messages
/app/chat.send  -> /topic/rooms/{roomId}/messages
/app/chat.leave -> /topic/rooms/{roomId}/messages
                -> /topic/rooms/{roomId}/users
```

## 2. 포함 프로젝트

```text
websocket_stomp_training_package
├── teacher-springex-websocket-stomp   # 정답 프로젝트
├── student-springex-websocket-stomp   # 학생용 TODO 프로젝트
└── README.md
```

## 3. 주요 파일

```text
src/main/java/org/scoula/domain
├── ChatMessage.java       # roomId, sender, content, type, sentAt, participantCount 추가
├── MessageType.java       # ENTER, CHAT, LEAVE, SYSTEM
└── UserListMessage.java   # 방별 접속자 목록 DTO

src/main/java/org/scoula/service
└── ChatRoomService.java   # 방별 사용자 목록 및 sessionId 매핑 관리

src/main/java/org/scoula/controller
├── ChatController.java           # join/send/leave 메시지 처리
└── WebSocketEventListener.java   # 브라우저 종료/연결 끊김 이벤트 처리

src/main/webapp/WEB-INF/views
└── index.jsp              # 방 ID, 이름, 접속자 목록 UI 추가

src/main/webapp/resources/js
└── stomp.js               # 방별 topic 구독, 입장/채팅/퇴장 메시지 발행
```

```java
@MessageMapping("/chat.send")
public void send(ChatMessage message) {
    ChatMessage chatMessage = chatRoomService.createChatMessage(message);
    messagingTemplate.convertAndSend(
        "/topic/rooms/" + chatMessage.getRoomId() + "/messages",
        chatMessage
    );
}
```

특징:

- `roomId`별 메시지 분리
- `/topic/rooms/lobby/messages`, `/topic/rooms/room1/messages`처럼 방별 Topic 사용
- `SimpMessagingTemplate`으로 동적 Destination 발행
- 접속자 목록 실시간 갱신
- 연결 종료 이벤트 처리

## 5. 핵심 개념 설명

### 5.1 `/chat-app`

WebSocket 최초 연결 Endpoint입니다.

```java
registry.addEndpoint("/chat-app")
        .setAllowedOriginPatterns("*");
```

브라우저는 다음 주소로 연결합니다.

```javascript
brokerURL: `ws://${location.host}/chat-app`;
```

`location.host`를 사용했기 때문에 Tomcat 포트가 8080이든 8081이든 자동으로 맞습니다.

### 5.2 `/app`

클라이언트가 서버 Controller로 메시지를 보낼 때 사용하는 Prefix입니다.

```java
config.setApplicationDestinationPrefixes("/app");
```

예:

```javascript
stompClient.publish({ destination: '/app/chat.send' });
```

서버에서는 다음 메서드가 실행됩니다.

```java
@MessageMapping("/chat.send")
```

### 5.3 `/topic`

클라이언트가 메시지를 받을 때 구독하는 Prefix입니다.

```java
config.enableSimpleBroker("/topic");
```

예:

```javascript
stompClient.subscribe('/topic/rooms/lobby/messages', callback);
```

### 5.4 `SimpMessagingTemplate`

`@SendTo`는 고정 Topic에 적합합니다. 하지만 채팅방은 `roomId`에 따라 Topic이 달라져야 합니다.

그래서 다음과 같이 동적 Topic을 사용합니다.

```java
messagingTemplate.convertAndSend(
    "/topic/rooms/" + roomId + "/messages",
    message
);
```

## 6. 멀티 사용자 메시지 흐름

### 6.1 사용자 입장

```text
브라우저 A
  -> CONNECT ws://localhost:8081/chat-app
  -> SUBSCRIBE /topic/rooms/lobby/messages
  -> SUBSCRIBE /topic/rooms/lobby/users
  -> SEND /app/chat.join
  -> 서버가 입장 메시지와 사용자 목록 발행
```

### 6.2 일반 채팅

```text
브라우저 A
  -> SEND /app/chat.send
  -> ChatController.send()
  -> ChatRoomService.createChatMessage()
  -> /topic/rooms/lobby/messages 발행
  -> 같은 방 구독자 전체 수신
```

### 6.3 사용자 퇴장

```text
끊기 버튼 또는 브라우저 종료
  -> /app/chat.leave 또는 SessionDisconnectEvent
  -> ChatRoomService.leave()
  -> 퇴장 메시지 발행
  -> 사용자 목록 갱신
```

## 7. 테스트 방법

1. 프로젝트를 IntelliJ에서 Gradle 프로젝트로 엽니다.
2. Tomcat 9에 WAR Artifact를 배포합니다.
3. Application context는 `/`로 설정합니다.
4. 브라우저에서 접속합니다.

```text
http://localhost:8081/
```

5. 브라우저 탭을 2개 이상 엽니다.
6. 같은 방 ID, 다른 이름으로 접속합니다.
7. 한쪽에서 보낸 메시지가 다른 탭에 표시되는지 확인합니다.
8. 한 탭을 닫았을 때 접속자 목록이 갱신되는지 확인합니다.

## 8. 정상 로그 예시

정상 연결 시 다음과 같이 CONNECT 수가 증가합니다.

```text
WebSocketSession[1 current WS(1), 1 total]
stompSubProtocol[processed CONNECT(1)-CONNECTED(1)]
```

메시지를 보내면 다음 로그가 출력됩니다.

```text
채팅 메시지 수신: ChatMessage(roomId=lobby, sender=홍길동, content=안녕하세요, type=CHAT, ...)
```

## 9. TODO 핵심 구현 포인트

- `ChatMessage`에 `roomId`, `sender`, `content`, `type`, `sentAt`, `participantCount` 추가
- `ChatRoomService`에서 `ConcurrentHashMap`으로 방별 사용자 관리
- `ChatController`에서 `@MessageMapping("/chat.join")`, `@MessageMapping("/chat.send")`, `@MessageMapping("/chat.leave")` 구현
- `SimpMessagingTemplate.convertAndSend()`로 동적 Topic 발행
- `stomp.js`에서 방별 Topic 구독
- `SessionDisconnectEvent`로 비정상 종료 처리

## 10. 실무 확장

현재 구현은 메모리 기반이므로 운영 서비스로 확장하려면 아래 기능을 추가해야 합니다.

- Redis Pub/Sub으로 다중 WAS 간 메시지 동기화
- DB에 채팅 메시지 저장
- JWT 인증 기반 사용자 식별
- 채팅방 생성/삭제 REST API
- 금칙어 필터링
- 메시지 읽음 처리
- 파일/이미지 메시지 처리
