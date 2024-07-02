<?php
include"bd.php";
 

if(isset($_GET['nom']) && isset($_GET['apep'])&& isset($_GET['apem'])&& isset($_GET['edad'])&& isset($_GET['tel'])&& isset($_GET['dom'])&& isset($_GET['ale'])&& isset($_GET['tipo'])&& isset($_GET['tutor'])&& isset($_GET['sexo'])&& isset($_GET['id']))

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
	$id = $_GET['id'];


	$bd = new B();
 
	$res = $bd->editar($nom,$apep,$apem,$edad,$tel,$dom,$ale,$tipo,$tutor,$sexo, $id);
	echo '{"id":'.$res.'}';
}