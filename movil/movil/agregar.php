<?php
include"bd.php";

if(isset($_GET['nom']) && isset($_GET['apep'])&& isset($_GET['apem'])&& isset($_GET['edad'])&& isset($_GET['tel'])&& isset($_GET['dom'])
&& isset($_GET['ale'])&& isset($_GET['tipo'])&& isset($_GET['tutor'])&& isset($_GET['sexo']))
{
	$nom = $_GET['nom'];
	$apep = $_GET['apep'];
	$apem = $_GET['apem'];
	$edad = $_GET['edad'];
	$tel = $_GET['tel'];
	$dom = $_GET['dom'];
	$ale = $_GET['ale'];
	$tipo = $_GET['tipo'];
	$tutor = $_GET['tutor'];
	$sexo = $_GET['sexo'];

	$bd = new B();
	$res = $bd->agregar($nom,$apep,$apem,$edad,$tel,$dom,$ale,$tipo,$tutor,$sexo);
	echo '{"id":'.$res.'}';
}  