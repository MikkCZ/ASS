<?php
	echo 'COOKIES: <pre>';
	echo htmlspecialchars(print_r($_COOKIE, true), ENT_QUOTES);
	echo '</pre>';
	if(isset($_SESSION)) {
		echo 'SESSION: <pre>';
		echo htmlspecialchars(print_r($_SESSION, true), ENT_QUOTES);
		echo '</pre>';
	}
	echo 'POST: <pre>';
	echo htmlspecialchars(print_r($_POST, true), ENT_QUOTES);
	echo '</pre>';
	echo 'GET: <pre>';
	echo htmlspecialchars(print_r($_GET, true), ENT_QUOTES);
	echo '</pre>';
?>
