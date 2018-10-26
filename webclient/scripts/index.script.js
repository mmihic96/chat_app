var socket = io();
var username = localStorage.getItem('username');
document.getElementById("welcome").innerHTML = username;
document.getElementById("welcome").style.color = "dodgerblue";

document.getElementById('message').addEventListener("keyup", function (event) {
    event.preventDefault();
    if (event.keyCode === 13) {
        document.getElementById("sendMsgBtn").click();
    }
});

function sendMessage() {
    var inputMessage = document.getElementById("message").value;
    if (!inputMessage || inputMessage === "") {
        return;
    }
    var message = username + ": " + inputMessage;
    socket.emit('clientChat', {
        msg: message
    });
    document.getElementById("message").value = "";
}

socket.on('clientChat', function (msg) {
    createMsgElemet(msg.msg, false);
});

socket.on('serverMessages', function (msg) {
    createMsgElemet(msg.msg, true);
});


function createMsgElemet(message, server) {
    var parent = document.getElementById('chatBox');
    var textnode;
    var div = document.createElement('div');
    div.setAttribute('class', 'container');
    var p = document.createElement("p");
    if (server) {
        textnode = document.createTextNode("Admin: " + message);
        p.setAttribute('style', "color:red");
    } else {
        textnode = document.createTextNode(message);
    }
    p.appendChild(textnode);
    div.appendChild(p);
    parent.appendChild(div);
    parent.scrollTop = parent.scrollHeight;
}