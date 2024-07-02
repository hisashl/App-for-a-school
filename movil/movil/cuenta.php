<?php
include"bd.php";

$bd = new B();
$res = $bd->cuenta();
echo '{"cue":'.$res.'}';
