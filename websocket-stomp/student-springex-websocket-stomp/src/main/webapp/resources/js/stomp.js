/*
 * stomp.js - 학생용 TODO 멀티 사용자 STOMP 채팅 클라이언트
 *
 * 변경 핵심:
 * 1. brokerURL을 ws://${location.host}/chat-app 으로 구성하여 Tomcat 포트가 8080/8081이어도 자동 대응합니다.
 * 2. /topic/chat 하나만 구독하던 구조를 /topic/rooms/{roomId}/messages 로 변경했습니다.
 * 3. 접속자 목록을 위해 /topic/rooms/{roomId}/users 를 추가 구독합니다.
 * 4. 입장, 일반 메시지, 퇴장 메시지를 각각 /app/chat.join, /app/chat.send, /app/chat.leave 로 분리했습니다.
 */
let stompClient = null;
let currentRoomId = null;
let currentSender = null;
let messageSubscription = null;
let userSubscription = null;

function setConnected(connected) {
    document.querySelector('#connect').disabled = connected;
    document.querySelector('#disconnect').disabled = !connected;
    document.querySelector('#roomId').disabled = connected;
    document.querySelector('#sender').disabled = connected;
    document.querySelector('#send').disabled = !connected;
}

function connect(event) {
    event.preventDefault();

    // TODO 1: 방 ID를 읽어 currentRoomId에 저장하세요.
    currentRoomId = document.querySelector('#roomId').value.trim() || 'lobby';
    currentSender = document.querySelector('#sender').value.trim();

    if (!currentSender) {
        alert('이름을 입력하세요.');
        return;
    }

    stompClient = new StompJs.Client({
        // TODO 2: 서버 WebSocket Endpoint와 일치하도록 brokerURL을 설정하세요.
        brokerURL: `ws://${location.host}/chat-app`,
        reconnectDelay: 5000,
        debug: function (str) {
            console.log(str);
        }
    });

    stompClient.onConnect = function () {
        setConnected(true);
        clearMessages();

        // TODO 3: 방별 메시지 topic을 구독하세요.
        messageSubscription = stompClient.subscribe(`/topic/rooms/${currentRoomId}/messages`, function (message) {
            const body = JSON.parse(message.body);
            showChatMessage(body);
        });

        // TODO 4: 방별 사용자 목록 topic을 구독하세요.
        userSubscription = stompClient.subscribe(`/topic/rooms/${currentRoomId}/users`, function (message) {
            const body = JSON.parse(message.body);
            renderUserList(body);
        });

        stompClient.publish({
            // TODO 5: 입장 메시지를 /app/chat.join으로 전송하세요.
            destination: '/app/chat.join',
            body: JSON.stringify({
                roomId: currentRoomId,
                sender: currentSender
            })
        });
    };

    stompClient.onWebSocketError = function (error) {
        console.error('WebSocket 오류:', error);
    };

    stompClient.onStompError = function (frame) {
        console.error('Broker 오류:', frame.headers['message']);
        console.error('상세:', frame.body);
    };

    stompClient.activate();
}

function disconnect(event) {
    event.preventDefault();

    if (stompClient && stompClient.connected) {
        stompClient.publish({
            destination: '/app/chat.leave',
            body: JSON.stringify({})
        });
    }

    if (messageSubscription) messageSubscription.unsubscribe();
    if (userSubscription) userSubscription.unsubscribe();

    if (stompClient) {
        stompClient.deactivate();
    }

    setConnected(false);
}

function sendMessage(event) {
    event.preventDefault();

    if (!stompClient || !stompClient.connected) {
        alert('먼저 연결하세요.');
        return;
    }

    const content = document.querySelector('#content').value.trim();
    if (!content) {
        alert('메시지를 입력하세요.');
        return;
    }

    stompClient.publish({
        // TODO 6: 일반 채팅 메시지를 /app/chat.send로 전송하세요.
            destination: '/app/chat.send',
        body: JSON.stringify({
            roomId: currentRoomId,
            sender: currentSender,
            content: content
        })
    });

    document.querySelector('#content').value = '';
}

function showChatMessage(message) {
    const tbody = document.querySelector('#chat-messages');
    const tr = document.createElement('tr');
    const td = document.createElement('td');

    if (message.type === 'ENTER' || message.type === 'LEAVE' || message.type === 'SYSTEM') {
        td.className = 'system';
        td.textContent = `[${message.roomId}] ${message.content} (현재 ${message.participantCount}명)`;
    } else {
        if (message.sender === currentSender) {
            td.className = 'me';
        }
        td.textContent = `${message.sender}: ${message.content}`;
    }

    tr.appendChild(td);
    tbody.appendChild(tr);
}

function renderUserList(userListMessage) {
    document.querySelector('#user-count').textContent = userListMessage.count;

    const userListDiv = document.querySelector('#user-list');
    userListDiv.innerHTML = '';

    userListMessage.users.forEach(function (user) {
        const div = document.createElement('div');
        div.textContent = user;
        userListDiv.appendChild(div);
    });
}

function clearMessages() {
    document.querySelector('#chat-messages').innerHTML = '';
    document.querySelector('#user-list').innerHTML = '';
    document.querySelector('#user-count').textContent = '0';
}

document.querySelector('#connect').addEventListener('click', connect);
document.querySelector('#disconnect').addEventListener('click', disconnect);
document.querySelector('#message-form').addEventListener('submit', sendMessage);
setConnected(false);
