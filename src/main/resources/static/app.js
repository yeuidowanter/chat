let stompClient = null;
let currentUser = null;

// 페이지 로드 시 사용자 확인
window.onload = function() {
    const username = localStorage.getItem('chatUsername');
    if (!username) {
        window.location.href = '/login.html';
        return;
    }

    // URL에서 roomId 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const roomId = urlParams.get('roomId');
    
    if (!roomId) {
        window.location.href = '/rooms.html';
        return;
    }
    
    currentUser = {
        userId: 'user-' + new Date().getTime(),
        username: username,
        status: 'ONLINE'
    };
    
    connect(roomId);
};

function connect(roomId) {
    console.log('Connecting to room:', roomId);
    
    // 먼저 이전 메시지들을 불러옴
    $.get(`/api/messages/${roomId}`, function(messages) {
        messages.forEach(message => {
            showMessage(message);
        });
    });

    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected:', frame);
        
        // roomId별로 구독
        stompClient.subscribe(`/topic/chat/${roomId}`, function (message) {
            showMessage(JSON.parse(message.body));
        });

        // 새로운 로그인일 때만 입장 메시지 전송
        const isNewLogin = localStorage.getItem('isNewLogin') === 'true';
        if (isNewLogin && currentUser) {
            console.log('Sending join message for:', currentUser);
            stompClient.send(`/app/join/${roomId}`, {}, JSON.stringify({
                sender: currentUser.username,
                content: `${currentUser.username} joined the chat`,
                type: 'JOIN',
                roomId: roomId
            }));
            localStorage.removeItem('isNewLogin');
        }
    });
}

function showMessage(message) {
    console.log('Showing message:', message);
    let messageContent = "";
    
    if (message.type === "JOIN" || message.type === "LEAVE") {
        messageContent = `<div class="system-message">${message.content}</div>`;
    } else {
        const isMyMessage = message.sender === currentUser.username;
        const messageClass = isMyMessage ? 'my-message' : 'other-message';
        const alignClass = isMyMessage ? 'ml-auto' : 'mr-auto';
        
        messageContent = `
            <div class="message-container ${alignClass}">
                <div class="chat-message ${messageClass}">
                    ${!isMyMessage ? `<div class="sender-name">${message.sender}</div>` : ''}
                    <div class="message-content">${message.content}</div>
                </div>
            </div>`;
    }
    
    $("#messages").append(messageContent);
}

function sendMessage() {
    const messageContent = $("#message").val().trim();
    if (!messageContent) return;

    const urlParams = new URLSearchParams(window.location.search);
    const roomId = urlParams.get('roomId');
    
    const message = {
        sender: currentUser.username,
        content: messageContent,
        type: 'CHAT',
        roomId: roomId
    };

    stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(message));
    $("#message").val('');
}

function logout() {
    const urlParams = new URLSearchParams(window.location.search);
    const roomId = urlParams.get('roomId');
    
    if (stompClient && stompClient.connected) {
        // 퇴장 메시지 전송
        stompClient.send(`/app/leave/${roomId}`, {}, JSON.stringify({
            sender: currentUser.username,
            content: `${currentUser.username} left the chat`,
            type: 'LEAVE',
            roomId: roomId
        }));
        
        // WebSocket 연결 종료
        stompClient.disconnect();
    }
    
    // localStorage 정리
    localStorage.removeItem('chatUsername');
    localStorage.removeItem('isNewLogin');
    
    // 로그인 페이지로 리다이렉트
    window.location.href = '/login.html';
}

// 이벤트 리스너
$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#send").click(sendMessage);
    $("#logout").click(logout);
    $("#message").keypress((e) => {
        if (e.which === 13) sendMessage();
    });
});