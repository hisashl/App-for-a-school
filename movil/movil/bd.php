 <?php
 class B
{
	private $con;

    private function conectar()
    {
        // Establece la conexión a la base de datos utilizando PDO
        $host = 'localhost';     // Dirección del servidor de la base de datos
        $dbname = 'bd_upa';      // Nombre de la base de datos
        $username = 'root';      // Nombre de usuario de la base de datos
        $password = '';          // Contraseña de la base de datos

        // Crea una instancia de PDO y almacénala en la propiedad $con
        $this->con = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    }


   
    public function ingreso($usr, $pass)
{
 $this->conectar();

    $sql = $this->con->prepare("SELECT * FROM perfil WHERE  usuario = :usr AND pass = :pass");
    $sql->bindParam(':usr', $usr);
    $sql->bindParam(':pass', $pass);
    $sql->execute();
    $res = $sql->fetchAll();
    if (count($res) > 0) {
          foreach ($res as $dato) return $dato['id'];

   }
    return -1;
}
    public function agregar($nom, $apep, $apem, $edad, $tel, $dom, $ale, $tipo, $tutor, $sexo)
    		{
             $this->conectar();

     		   $sql = $this->con->prepare("INSERT INTO clientes (nombre, apep, apem, edad, telefono, domicilio, alergias, tipodesangre, tutor, sexo) VALUES (:nom, :apep, :apem, :edad, :tel,:dom, :ale, :tipo, :tutor, :sexo)");
   	 	    $sql->bindParam(':nom', $nom);
   		     $sql->bindParam(':apep', $apep);
               $sql->bindParam(':apem', $apem);
  		       $sql->bindParam(':edad', $edad);
                  $sql->bindParam(':tel', $tel);
                   $sql->bindParam(':dom', $dom);
                    $sql->bindParam(':ale', $ale);
                     $sql->bindParam(':tipo', $tipo);
                      $sql->bindParam(':tutor', $tutor);
                       $sql->bindParam(':sexo', $sexo);
     			   $sql->execute();
   			 $id = $this->con->lastInsertId();
       			 if ($id> 0) {
      			      return $id;
      			  }
       				 return -1;
   			 }
			public function cuenta() {
             $this->conectar();

   			 $sql = $this->con->prepare("SELECT COUNT(id) as total_filas FROM clientes");
   			 $sql->execute();
   			 $resultado = $sql->fetch(PDO::FETCH_ASSOC);
    
   			 if ($resultado) {
    			    return $resultado['total_filas'];
  			  } else {
    			    return -1; // Error en la consulta
   			 }
			}
           
            public function mostrar($id){
             $this->conectar();

			$sql = $this->con->prepare("SELECT * FROM clientes WHERE id = :id");
			$sql->bindParam(':id', $id);
			$sql->execute();
			$res = $sql->fetchAll(PDO::FETCH_ASSOC);    
			if (count($res) > 0) {
				return $res[0]; // Devuelve el primer registro encontrado
			} else {
				return null; // No se encontró ningún registro con el pk dado
			}
		}
public function editar($nom, $apep, $apem, $edad, $tel, $dom, $ale, $tipo, $tutor, $sexo, $id){
   $this->conectar();

    $sql = $this->con->prepare("UPDATE clientes SET nombre=:nom, apep=:apep, apem=:apem, edad=:edad, telefono=:tel, domicilio=:dom, alergias=:ale, tipodesangre=:tipo, tutor=:tutor, sexo=:sexo WHERE id=:id");
        $sql->bindParam(':nom', $nom);
    		 $sql->bindParam(':apep', $apep);
             $sql->bindParam(':apem', $apem);
  	         $sql->bindParam(':edad', $edad);
             $sql->bindParam(':tel', $tel);
             $sql->bindParam(':dom', $dom);
             $sql->bindParam(':ale', $ale);
             $sql->bindParam(':tipo', $tipo);
             $sql->bindParam(':tutor', $tutor);
             $sql->bindParam(':sexo', $sexo);
             $sql->bindParam(':id', $id);
        $sql->execute();
        $rowCount = $sql->rowCount();
        if ($rowCount > 0) {
        return $rowCount; // Devuelve el número de filas afectadas por la actualización
        }
         return -1;
}
public function eliminar($id)
{
 $this->conectar();

    $sql = $this->con->prepare("DELETE FROM clientes WHERE id = :id");
    $sql->bindParam(':id', $id);
    $sql->execute();
    $rowCount = $sql->rowCount();
    if ($rowCount > 0) {
        return $rowCount; // Devuelve el número de filas afectadas por la eliminación
    }
    return -1;
}
}
?>
