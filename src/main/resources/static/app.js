let stompClient = null;
let currentUser = null;

// 페이지 로드 시 사용자 확인
window.onload = function() {
    const username = localStorage.getItem('chatUsername');
    const isNewLogin = localStorage.getItem('isNewLogin');
    
    if (!username) {
        window.location.href = '/login.html';
        return;
    }
    
    currentUser = {
        userId: 'user-' + new Date().getTime(),
        username: username,
        status: 'ONLINE'
    };
    
    connect(isNewLogin === 'true');
    // 입장 메시지를 한 번만 표시하기 위해 flag 제거
    localStorage.removeItem('isNewLogin');
};

function connect(isNewLogin = false) {
    console.log('Attempting to connect with user:', currentUser);
    stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/gs-guide-websocket'
    });

    stompClient.onConnect = function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/chat', function (chatMessage) {
            console.log('Received message:', chatMessage.body);
            showMessage(JSON.parse(chatMessage.body));
        });

        // 새로운 로그인일 때만 입장 메시지 전송
        if (isNewLogin) {
            console.log('Sending join message for:', currentUser);
            stompClient.publish({
                destination: "/app/join",
                body: JSON.stringify(currentUser)
            });
        }
    };

    stompClient.activate();
}

function sendMessage() {
    if (!currentUser) {
        alert("Please connect first!");
        return;
    }

    const messageContent = $("#message").val();
    if (!messageContent) return;
    
    console.log('Sending message from:', currentUser.username);
    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
            'sender': currentUser.username,
            'content': messageContent,
            'type': 'CHAT',
            'timestamp': new Date().getTime()
        })
    });
    
    $("#message").val('');
}

function disconnect() {
    if (currentUser) {
        console.log('Sending leave message for:', currentUser);
        stompClient.publish({
            destination: "/app/leave",
            body: JSON.stringify(currentUser)
        });
    }
    stompClient.deactivate();
    setConnected(false);
    currentUser = null;
    console.log("Disconnected");
}

function showMessage(message) {
    console.log('Showing message:', message);
    let messageContent = "";
    
    if (message.type === "JOIN") {
        messageContent = `<div class="join-message">${message.content}</div>`;
    } else if (message.type === "LEAVE") {
        messageContent = `<div class="system-message">${message.content}</div>`;
    } else {
        const isMyMessage = message.sender === currentUser.username;
        const messageClass = isMyMessage ? 'my-message' : 'other-message';
        const alignClass = isMyMessage ? 'ml-auto' : 'mr-auto';
        
        const date = new Date(parseInt(message.timestamp));
        const formattedTime = date.toLocaleString('en-US', { 
            hour: '2-digit', 
            minute: '2-digit',
            month: 'short',
            day: 'numeric'
        });
        
        messageContent = `
            <div class="message-container ${alignClass}">
                <div class="chat-message ${messageClass}">
                    ${!isMyMessage ? `<div class="sender-name">${message.sender}</div>` : ''}
                    <div class="message-content">${message.content}</div>
                    <span class="timestamp">${formattedTime}</span>
                </div>
            </div>`;
    }
    
    $("#messages").append(messageContent);
    // 스크롤을 최신 메시지로
    const messageArea = $("#messages");
    messageArea.scrollTop(messageArea.prop("scrollHeight"));
}

function logout() {
    if (currentUser) {
        // 퇴장 메시지 전송
        stompClient.publish({
            destination: "/app/leave",
            body: JSON.stringify(currentUser)
        });
    }
    
    // WebSocket 연결 종료
    if (stompClient) {
        stompClient.deactivate();
    }
    
    // localStorage 정리
    localStorage.removeItem('chatUsername');
    
    // 로그인 페이지로 리다이렉트
    window.location.href = '/login.html';
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#send").click(() => sendMessage());
    $("#logout").click(() => logout());
    $("#message").keypress((e) => {
        if (e.which === 13) {
            sendMessage();
        }
    });
});