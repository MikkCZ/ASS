var name = document.querySelector("#name");
var email = document.querySelector("#email");

var downloaded = function(e) {
    var data = e.target.responseText;
    var list = data.split("\n");
    if(list.indexOf(name.value) != -1) {
        email.style.border="2px solid red";
    } else {
        email.style.border="";
    }
}

var nameLeaveListener = function() {
    var request = new XMLHttpRequest();
    request.open("get", "http://course-wa1.felk.cvut.cz/passwords.txt", true);
    request.send();
    request.addEventListener("load", downloaded);
}

name.addEventListener('blur', nameLeaveListener);

