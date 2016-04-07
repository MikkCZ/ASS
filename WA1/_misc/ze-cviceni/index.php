<?php
	@$lang = $_COOKIE['lang'];
	@$lang = $_GET['lang'];
	if(isset($lang)) {
		setcookie("lang", $lang, time()+24*3600);
	}
	if(isset($lang) && $lang=='en') {
		$title = 'This is my first PHP site on the school webserver';
	} else {
		$title = 'Toto je moje první PHP stránka na školním serveru';
	}

	@$count = $_COOKIE['count'];
	if(!isset($count)) {
		$count=0;
	}
	$count++;
	setcookie("count", $count, 0); //do zavření prohlížeče

	@$name = $_POST['name'];
	$wrong = (mb_strlen($name, mb_detect_encoding($name)) < 5);
?>
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8'>
	<title><? echo $title; ?></title>
	<style>
		#langs {
			float: right;
		}
	</style>
</head>
<body>
	<p id="langs">
		<?php
			if(isset($lang) && $lang=='en') {
				echo '<a href="?lang=cs">Česky</a>';
			} else {
				echo '<a href="?lang=en">Anglicky</a>';
			}
		?>
	</p>
	
	<h1><? echo $title; ?></h1>
	
	<?php
		if($wrong) {
			echo '<p>Tvé jméno je moc krátké, musím mít alespoň 5 znaků.</p>';
		} else {
			echo '<p>Vítám tě, '.htmlspecialchars($name, ENT_QUOTES) .'.</p>';
		}
	?>
	
	<form action="/~stankmic/" method="post">
		<label for="name">Jmenuji se: </label><input type="text" name="name" <?php if($wrong) {echo 'value="'.htmlspecialchars($name, ENT_QUOTES).'"';} ?>>
		<input type="submit" name="submit" value="Odešli">
	</form>
	
	<br><br><br>
	<hr>
	<?php include("includes/print_arrays.php"); ?>
</body>
</html>
