let webSocket;
let button1 = document.getElementById("button1");

let commentaar = document.getElementById("pog");



button1.addEventListener("click",send,false);

// ------------------- Websockets -------------------- //


function send() {
    let text = document.getElementById("user").value;
    let desc = document.getElementById("desc").value;
    let rate = document.getElementById("rate").value;
    console.log(text);
    console.log(desc);
    console.log(rate);

    webSocket.send(text);
}


function writeResponse(data) {
    console.log("kekw");
    console.log(data);
    commentaar.innerHTML += "<br/>" + data;
}

function openSocket() {

    webSocket = new WebSocket("ws://localhost:8080/comment");

    webSocket.onopen = function (event) {
        console.log("Opened");
        writeResponse("Opened");
    };

    webSocket.onmessage = function(event){
        // console.log("test");
        // console.log(event.data);
        // addComment(event.data);
        writeResponse(event.data);
    };

    webSocket.onclose = function (event) {
        console.log("Closed");
    };
}

function showComments() {
    writeResponse();

}

function addComment(name, desc, rating) {
    console.log("starting comment");
    xhr.open('POST', "/addcomment/" + name + "/" + desc + "/" + rating);
    xhr.onreadystatechange = getComments;
    xhr.send(null);

}

function getComments() {
    xhr.open('GET', '/comments', true);
    xhr.onreadystatechange = showComments;
    xhr.send(null);
}

