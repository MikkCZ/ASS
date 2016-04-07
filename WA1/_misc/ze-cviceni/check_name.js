var checkIfNameUsedOnLeaveOrStopTyping = function() {
	var name = document.getElementById("name");			// select name input and its parent <p>
	var nameP = name;
	while(nameP.nodeName.toLowerCase() !== "p") {
		nameP=nameP.parentNode;
	}
	var used = document.createElement("span");			// create span#name_used for potential appending into the <p>
	used.setAttribute("class", "name_used");
	used.innerHTML = "Použité";

	var displayIfNameUsed = function(e) {				// change name input class and append span#name_used if the name is used
		var name_used = e.target.responseText;
		if(name_used === 1) {
		    name.setAttribute("class", "name_used");
		    if(used.parentNode !== nameP) {
		        nameP.appendChild(used);
		    }
		} else {
		    name.setAttribute("class", "name_free");
		    if(used.parentNode === nameP) {
		        nameP.removeChild(used);
		    }
		}
	};

	var URL = location.href;							// prepare check script URL
	var dirURL = URL.substring(0, URL.lastIndexOf('/'));
	var scriptURL = dirURL+"/check_user_in_db.php";

	var checkNameUsed = function() {					// check the filled name is used
		var request = new XMLHttpRequest();
		request.open("get", scriptURL+"?name="+name.value, true);
		request.send();
		request.addEventListener("load", displayIfNameUsed);
	};

	name.addEventListener("blur", checkNameUsed);		// check the filled name is used after leaving the input

	var typingTimer;									// create timer 
	var typingInterval = 1500;

	name.addEventListener("keyup", function(){			// on keyup start counting from the beginning and set the timout
		clearTimeout(typingTimer);
		typingTimer = setTimeout(checkNameUsed, typingInterval);	// when timeout interval passes, check the filled name is used
	});

	name.addEventListener("keydown", function(){		// on keydown stop counting
		clearTimeout(typingTimer);
	});
};

window.addEventListener("DOMContentLoaded", checkIfNameUsedOnLeaveOrStopTyping);

