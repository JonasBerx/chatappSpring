let webSocket;
let blog1 = document.getElementById("1").getElementsByClassName("comment")[0];
console.log(blog1);
blog1.addEventListener("click",addComment, false);


// ------------------- Websockets -------------------- //

function addComment() {
    let name = document.getElementById("user1").value;
    let desc = document.getElementById("comment1").value;
    let rating = document.getElementById("rating1").value;
    comment(name,desc,rating);
}

function comment(name,desc,rating) {

    console.log(name);
    console.log(desc);
    console.log(rating);

    if (xhr.status === 200){
        if (xhr.readyState === 4) {
            let table = document.getElementById("t1");
            let tr = document.createElement('tr');
            let tdname = document.createElement('td');
            tdname.innerText = name;
            let tdComment = document.createElement('td');
            tdComment.innerText = desc;
            let tdRating = document.createElement('td');
            tdRating.innerText = rating;
            tr.appendChild(tdname);
            tr.appendChild(tdComment);
            tr.appendChild(tdRating);
            tr.className = 'tablegedoe';
            table.appendChild(tr);
        }

    }
}

function getComments() {
    xhr.open('GET','/comments', true);
    xhr.onreadystatechange = comment;
    xhr.send(null);
}
