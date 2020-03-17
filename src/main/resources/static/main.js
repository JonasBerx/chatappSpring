let xhr = new XMLHttpRequest();
let button = document.getElementById("statusButton");

let add = document.getElementById("addfriend");
add.addEventListener("click",addFriend,false);
button.addEventListener("click",setstatus,false);

let timeoutID;
getFriendlist();



function setstatus(){
    let status = document.getElementById("statusInput").value;
    change(status);

}

function change(status){
    xhr.open('POST', '/change/' + status, true);
    xhr.onreadystatechange = update;
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}

function update(){
    if (xhr.status === 200){
        if (xhr.readyState === 4){
            document.querySelector('h3').innerText = xhr.responseText;
        }
    }
}

function getFriendlist(){
    xhr.open('GET','/friends', true);
    xhr.onreadystatechange = showFriends;
    xhr.send(null);
}


function showFriends(){
    if (xhr.status === 200){
        if (xhr.readyState === 4){
            clearTable();
            let text = JSON.parse(xhr.responseText);
            let table = document.getElementById('friends');
            for (let person in text){
                let tr = document.createElement('tr');
                let tdname = document.createElement('td');
                tdname.innerText = text[person].name;
                let tdstatus = document.createElement('td');
                tdstatus.innerText = text[person].statusname;
                tr.appendChild(tdname);
                tr.appendChild(tdstatus);
                tr.className = 'friendlist';
                table.appendChild(tr);
            }
            timeoutid = setTimeout(getFriendlist,10000);
        }
    }
}

function clearTable(){
    let friends = document.querySelectorAll('.friendlist');
    let table = document.getElementById('friends');
    for (let i=0;i<friends.length;i++){
        table.removeChild(friends[i]);
    }
}

function addFriend() {
    clearTimeout(timeoutid);
    console.log(document.getElementById("name").value);
    xhr.open("POST", "/addfriend/" + document.getElementById('name').value, true);
    xhr.onreadystatechange = getFriendlist;
    xhr.send(null);


}