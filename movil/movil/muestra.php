 <?php
 
include "bd.php";

// Verifica si se ha enviado un par�metro "id" a trav�s de la URL
if (isset($_GET['id'])) {
    // Obtiene el valor del par�metro "id"
    $id = $_GET['id'];

    // Crea una instancia de la clase BaseDeDatos
    $bd = new B();

    // Llama al m�todo "mostrar" de la clase BaseDeDatos y pasa el valor del par�metro "id"
    $resultado = $bd->mostrar($id);

    // Verifica si se obtuvo un resultado v�lido
    if ($resultado) {
        // Obtiene los valores de los campos del resultado
        $nombre = $resultado['nombre'];
        $apellidop = $resultado['apep'];
        $apellidom = $resultado['apem'];
        $edad = $resultado['edad'];
        $telefono = $resultado['telefono'];
        $domicilio = $resultado['domicilio'];
        $alergias = $resultado['alergias'];
        $tipodesangre = $resultado['tipodesangre'];
        $tutor = $resultado['tutor'];
        $sexo = $resultado['sexo'];

        // Crea un array asociativo con los valores obtenidos
        $response = array(
            "nom" => $nombre,
            "apep" => $apellidop,
            "apem" => $apellidom,
            "edad" => $edad,
            "tel" => $telefono,
            "dom" => $domicilio,
            "ale" => $alergias,
            "tipo" => $tipodesangre,
            "tutor" => $tutor,
            "sexo" => $sexo
        );

        // Convierte el array en formato JSON y lo imprime
        echo json_encode($response);
    }
}
?>
