<?php
include "bd.php";
if (isset($_GET['id'])) {
    	$id = $_GET['id'];
	$bd = new B();
	$res = $bd->eliminar($id);
	echo '{"eliminar":'.$res.'}';
}	