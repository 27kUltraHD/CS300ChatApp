/*
    Helper functions
 */

// small helper for selecting element by id
let id = id => document.getElementById(id);

// Establish a WebSocket connection
let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/index");

// Pseudocode: On event => doThis()
ws.onopen = () => alert("Connected");
ws.onmessage = msg => updateChat(msg);
ws.onclose = ()=> alert("WebSocket connection closed");

// Add event listeners to button and input field
//id("send").addEventListener("click", () => sendAndClear(id("message").value));
id("message").addEventListener("keypress", function(e) {
    // send message if 'enter' is pressed
    if(e.keyCode === 13) {
        sendAndClear(e.target.value);
    }
});

let clicked = false;
// highlights a user name on the list when double clicked
document.querySelector("ul").addEventListener("dblclick", function(e) {
   var selected;
    if(clicked === true){
        clicked = false;
        if(e.target.tagName === "LI") {
            selected = document.querySelector("li.selected");
            if(selected)
                selected.className = "";
            e.target.className = "selected";
            console.log(selected);
        }
    }

    else {
        clicked = true;

        if(e.target.tagName === "LI") {
            selected = document.querySelector("li.selected");
            if(selected)
                selected.className = "";
            e.target.className = "selected";
            console.log(selected);
        }
    }

});

// send message via websocket and reset message value
function sendAndClear(message) {
    if(message !== "") {
        ws.send(message);
        id("message").value ="";
    }
}

function updateChat(msg) {
    let data = JSON.parse(msg.data);
    id("chat").insertAdjacentHTML("afterbegin", data.userMessage);
    id("userlist").innerHTML = data.userlist.map(user => "<li>" + user + "</li>").join("");
}
