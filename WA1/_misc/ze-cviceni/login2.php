<?php
	function register_new_user($login, $password) {
		$db = new PDO('mysql:host=localhost;dbname=stankmic','stankmic','wa1');
		$stmt = $db->prepare('INSERT into users (username,password) VALUES (?,?)');
		$ok = $stmt->execute(array($login, crypt($password)));
		return $ok;
	}
	
	function checkLogin($login, $password) {
		$db = new PDO('mysql:host=localhost;dbname=stankmic','stankmic','wa1');
		$stmt = $db->prepare('SELECT password FROM users WHERE username = ?');
		$stmt->execute(array($_POST['login']));
		$pass = $stmt->fetch(PDO::FETCH_NUM)[0];

		$login = (crypt($_POST['password'], $pass) == $pass);
		
		return $login;
	}
	function logout() {
		$_SESSION['prihlasen']=FALSE;
	}
	function isLogedIn() {
		if(!isset($_SESSION['lastAccess']) || $_SESSION['lastAccess'] < time()-10*60) {
			logout();
		} else {
			$_SESSION['lastAccess']=time();
		}
		return isset($_SESSION['prihlasen']) && $_SESSION['prihlasen'];
	}
	// END functions
	
	session_start();
	if(isset($_POST['submit']) && $_POST['submit'] == 'Registrovat') {
		if(isset($_POST['login']) && isset($_POST['password'])) {
			$ok = register_new_user($_POST['login'],$_POST['password']);
			if($ok) {
				echo 'OK';
			} else {
				echo 'KO';
			}
		}
	} else if(isset($_POST['submit']) && $_POST['submit'] == 'Přihlásit') {
		if(isset($_POST['login']) && isset($_POST['password'])) {
			$_SESSION['prihlasen']=checkLogin($_POST['login'], $_POST['password']);
			$_SESSION['user']=$_POST['login'];
			$_SESSION['lastAccess']=time();
		}
	}
	
	if(isset($_GET['logout'])) {
		logout();
		session_destroy();
	}
	
	$loggedIn = isLogedIn();
?>
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8'>
	<title>Přihlašovací stránka</title>
	<style>
		form label {
			display: inline-block;
			width: 70px;
		}
	</style>
</head>
<body>
	<form action="./login2.php" method="post">
		<label for="login">Jméno:</label><input type="text" name="login"><br>
		<label for="password">Heslo:</label><input type="password" name="password"><br>
		<input type="submit" name="submit" value="Registrovat">
	</form>
	
	<?php
		if($loggedIn) {
			echo '<p>Jste přihlášen, '.$_SESSION['user'].'. Chcete se <a href="?logout">odhlásit</a>?</p>';
		} else {
			if(isset($_POST['login']) && isset($_POST['password'])) {
				echo '<p>Zadali jste neplatné přihlašovací údaje.</p>';
			}
			echo '
				<p>Jste odhlášen. Chcete se přihlásit?</p>
				<form action="./login2.php" method="post">
					<label for="login">Jméno:</label><input type="text" name="login"><br>
					<label for="password">Heslo:</label><input type="password" name="password"><br>
					<input type="submit" name="submit" value="Přihlásit">
				</form>
			';
		}
	?>
	
	<br><br><br>
	<hr>
	<?php include("includes/print_arrays.php"); ?>
</body>
</html>
