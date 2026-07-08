<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring WebSocket STOMP Multi User Chat</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>
    <style>
        body { padding-top: 20px; }
        #chat-messages td.system { color: #777; font-style: italic; }
        #chat-messages td.me { font-weight: bold; }
        #user-list { min-height: 120px; border: 1px solid #ddd; padding: 10px; }
    </style>
</head>
<body>
<div id="main-content" class="container">
    <h3>Spring WebSocket STOMP 멀티 사용자 채팅</h3>
    <p class="text-muted">
        같은 방 ID를 입력한 브라우저끼리만 메시지를 주고받습니다.
        테스트할 때는 브라우저 탭을 2개 이상 열고 같은 방 ID로 접속하세요.
    </p>

    <div class="row">
        <div class="col-md-4">
            <div class="form-group">
                <label for="roomId">방 ID</label>
                <input type="text" id="roomId" class="form-control" value="lobby" placeholder="예: lobby, room1">
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label for="sender">이름</label>
                <input type="text" id="sender" class="form-control" placeholder="이름을 입력하세요.">
            </div>
        </div>
        <div class="col-md-4" style="padding-top: 25px;">
            <button id="connect" class="btn btn-primary">연결</button>
            <button id="disconnect" class="btn btn-default" disabled="disabled">끊기</button>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-md-8">
            <form id="message-form" class="form-inline">
                <div class="form-group" style="width: 80%;">
                    <label for="content">메시지</label>
                    <input type="text" id="content" class="form-control" style="width: 80%;" placeholder="메시지를 입력하세요...">
                </div>
                <button id="send" class="btn btn-success" type="submit">Send</button>
            </form>

            <table class="table table-striped" style="margin-top: 20px;">
                <thead>
                <tr><th>채팅 메시지</th></tr>
                </thead>
                <tbody id="chat-messages"></tbody>
            </table>
        </div>

        <div class="col-md-4">
            <h4>현재 접속자 <span id="user-count" class="badge">0</span></h4>
            <div id="user-list"></div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/stomp.js"></script>
</body>
</html>
