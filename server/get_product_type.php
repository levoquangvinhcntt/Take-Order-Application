<?php 
	
	include "connect.php";

	$query = "SELECT * FROM product_type ";
	$result = $conn->query($query);

	$array_product_type = array();
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			array_push($array_product_type, new Product_Type($row['TYPE_ID'],$row['NAME'],$row['IMG'])); 
		}
	}
	
	echo json_encode($array_product_type);

	class Product_Type{
		function Product_Type($type_id,$name,$img){
			$this->type_id = $type_id;
			$this->name = $name;
			$this->img = $img;
		}
	}

	$conn->close();

 ?>