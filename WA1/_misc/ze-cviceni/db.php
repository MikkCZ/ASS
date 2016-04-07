<?php
	function checkLogin($login, $password) {
		$credentials = array(
			'user'=>'pass',
			'stankmic'=>'sta',
			'guest'=>'heslo',
			'kdokoliv'=>'cokoliv'
		);
		return isset($credentials[$login]) && $credentials[$login]==$password;
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
	if(isset($_POST['login']) && isset($_POST['password'])) {
		$_SESSION['prihlasen']=checkLogin($_POST['login'], $_POST['password']);
		$_SESSION['user']=$_POST['login'];
		$_SESSION['lastAccess']=time();
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
		table {
			border: 1px solid black;
		}
		table#test_table th,
		table#test_table td {
			border-left: 1px solid black;
			border-right: 1px solid black;
		}
	</style>
</head>
<body>

	<?php
		$db = new PDO('mysql:host=localhost;dbname=stankmic','stankmic','wa1');
		var_dump($db); echo '<br>';
		if(isset($_GET['id']) && is_numeric($_GET['id'])) {
			$result = $db->query('SELECT * FROM test WHERE id='.intval($_GET['id']),PDO::FETCH_ASSOC);
		} else {
			$result = $db->query('SELECT * FROM test',PDO::FETCH_ASSOC);
		}
		var_dump($result); echo '<br>';
		echo '<table id="test_table">';
		// TODO headers
		$i=0;
		foreach ($result as $row) {
			if($i == 0) {
				echo '<thead><tr>';
				foreach($row as $key=>$value) {
					echo '<th>';
					echo $key;
					echo '</th>';
				}
				echo '</tr></thead>';
			}
			echo '<tr>';
			foreach ($row as $cell) {
				echo '<td>';
				echo $cell;
				echo '</td>';
			}
			echo '</tr>';
			$i++;
		}
		echo '</table>';
	?>
	
	<form action="./db.php" method="get">
		<label for="id">ID:</label>
		<input type="text" name="id" id="id"><br>
		<input type="submit" name="submit" value="Filtruj">
	</form>
	
	<?php
		/*
		if($loggedIn) {
			echo '<p>Jste přihlášen, '.$_SESSION['user'].'. Chcete se <a href="?logout">odhlásit</a>?</p>';
		} else {
			if(isset($_POST['login']) && isset($_POST['password'])) {
				echo '<p>Zadali jste neplatné přihlašovací údaje.</p>';
			}
			echo '
				<p>Jste odhlášen. Chcete se přihlásit?</p>
				<form action="./login.php" method="post">
					<label for="login">Jméno:</label><input type="text" name="login"><br>
					<label for="password">Heslo:</label><input type="password" name="password"><br>
					<input type="submit" name="submit" value="Přihlásit">
				</form>
			';
		}
		*/
	?>
	
	<br><br><br>
	<hr>
	<?php include("includes/print_arrays.php"); ?>
</body>
</html>
