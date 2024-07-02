<?php
include"bd.php";

if(isset($_GET['usr']) && isset($_GET['pass']))
{
	$usr = $_GET['usr'];
	$pass = $_GET['pass'];

	$bd = new B();
	$res = $bd->ingreso($usr,$pass);
	echo '{"usr":'.$res.'}';
}