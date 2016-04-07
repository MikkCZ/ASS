var signupFormValidation = function () {
    var username = document.getElementById("username");
    username.wrong = true;
    var email = document.getElementById("email");
    email.wrong = true;
    var password = document.getElementById("password");
    password.wrong = true;
    var password2 = document.getElementById("password2");

    var labels = document.getElementsByTagName('label');
    for (var i = 0; i < labels.length; i++) {
        switch (labels[i].htmlFor) {
            case 'username':
                username.label = labels[i];
                continue;
            case 'email':
                email.label = labels[i];
                continue;
            case 'password':
                password.label = labels[i];
                continue;
            default :
                break;
        }
    }

    if (username.label.childElementCount !== 0 && username.label.children.item(0).nodeName.toLowerCase() === "span") {
        username.label.span = username.label.children.item(0);
    } else {
        username.label.span = document.createElement("span");
    }
    username.label.span.setAttribute("class", "warning");
    username.label.span.innerHTML = "Uživatel již existuje";

    if (email.label.childElementCount !== 0 && email.label.children.item(0).nodeName.toLowerCase() === "span") {
        email.label.span = email.label.children.item(0);
    } else {
        email.label.span = document.createElement("span");
    }
    email.label.span.setAttribute("class", "warning");
    email.label.span.innerHTML = "Email již existuje";

    if (password.label.childElementCount !== 0 && password.label.children.item(0).nodeName.toLowerCase() === "span") {
        password.label.span = password.label.children.item(0);
    } else {
        password.label.span = document.createElement("span");
    }
    password.label.span.setAttribute("class", "warning");

    var displayIfUsed = function (e, input) {
        var used = e.target.responseText;
        var parent = input.label;
        var span = input.label.span;
        if (used === '1') {
            input.setAttribute("class", "wrong");
            input.wrong = true;
            if (span.parentNode !== parent) {
                parent.appendChild(span);
            }
            return true;
        } else {
            input.setAttribute("class", "ok");
            input.wrong = false;
            if (span.parentNode === parent) {
                parent.removeChild(span);
            }
            return false;
        }
    };

    var displayIfUsernameUsed = function (e) {
        return displayIfUsed(e, username);
    };

    var displayIfEmailUsed = function (e) {
        return displayIfUsed(e, email);
    };

    var displayIfPasswordWrong = function (wrong) {
        if (!wrong) {
            password.setAttribute("class", "ok");
            password2.setAttribute("class", "ok");
            password.wrong = false;
            if (password.label.span.parentNode === password.label) {
                password.label.removeChild(password.label.span);
            }
            return false;
        } else {
            password.setAttribute("class", "wrong");
            password2.setAttribute("class", "wrong");
            password.wrong = true;
            if (password.label.span.parentNode !== password.label) {
                password.label.appendChild(password.label.span);
            }
            return true;
        }
    };

    var URL = location.href;
    var dirURL = URL.substring(0, URL.lastIndexOf('/'));
    var scriptURL = dirURL + "/js/api/signup.php";

    var checkUsernameUsed = function () {
        var request = new XMLHttpRequest();
        request.open("get", scriptURL + "?username=" + username.value, true);
        request.send();
        request.addEventListener("load", displayIfUsernameUsed);
    };

    var checkEmailUsed = function () {
        var request = new XMLHttpRequest();
        request.open("get", scriptURL + "?email=" + email.value, true);
        request.send();
        request.addEventListener("load", displayIfEmailUsed);
    };

    var checkPasswordMatch = function () {
        var p1 = password.value;
        var p2 = password2.value;
        return !displayIfPasswordMismatch(p1 === p2);
    };

    var checkPassword = function () {
        var short = (password.value.length < 6);
        if (short) {
            password.label.span.innerHTML = "Heslo je kratší než 6 znaků";
            return !displayIfPasswordWrong(short);
        } else {
            displayIfPasswordWrong(short);
        }
        var p1 = password.value;
        var p2 = password2.value;
        var match = (p1 == p2);
        if (!match) {
            password.label.span.innerHTML = "Hesla nesouhlasí";
            return !displayIfPasswordWrong(!match);
        } else {
            displayIfPasswordWrong(!match);
        }

    };

    username.addEventListener("blur", checkUsernameUsed);
    email.addEventListener("blur", checkEmailUsed);
    password.addEventListener("blur", checkPassword);
    password2.addEventListener("blur", checkPassword);

    var typingTimer;
    var typingInterval = 750;

    var clearTypingTimer = function () {
        clearTimeout(typingTimer);
    };

    username.addEventListener("keyup", function () {
        clearTypingTimer();
        typingTimer = setTimeout(checkUsernameUsed, typingInterval);
    });

    email.addEventListener("keyup", function () {
        clearTypingTimer();
        typingTimer = setTimeout(checkEmailUsed, typingInterval);
    });

    password.addEventListener("keyup", function () {
        clearTypingTimer();
        typingTimer = setTimeout(checkPassword, typingInterval);
    });

    password2.addEventListener("keyup", function () {
        clearTypingTimer();
        typingTimer = setTimeout(checkPassword, typingInterval);
    });

    username.addEventListener("keydown", clearTypingTimer);

    email.addEventListener("keydown", clearTypingTimer);

    password.addEventListener("keydown", clearTypingTimer);

    password2.addEventListener("keydown", clearTypingTimer);

    var form = username;
    while (form.nodeName.toLowerCase() !== "form") {
        form = form.parentNode;
    }

    form.error = form.parentNode.querySelectorAll(".error");

    if (form.error.length !== 0 && form.error.item(0).nodeName.toLowerCase() === "div") {
        form.error.div = form.error.item(0);
    } else {
        form.error.div = document.createElement("div");
    }
    form.error.div.setAttribute("class", "error");
    form.error.div.innerHTML = "Zkontrolujte prosím vyplnění formuláře níže.";

    var validateForm = function (e) {
        if (username.wrong || email.wrong || password.wrong) {
            e.preventDefault();
            if (form.error.div.parentNode !== form.parentNode) {
                form.parentNode.insertBefore(form.error.div, form);
            }
        } else {
            if (form.error.div.parentNode === form.parentNode) {
                form.parentNode.removeChild(form.error.div);
            }
        }
    };

    form.addEventListener("submit", validateForm);
};

window.addEventListener("DOMContentLoaded", signupFormValidation);
