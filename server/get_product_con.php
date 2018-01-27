<?php 
	
	include "connect.php";
	$name = $_POST['name'];
	$query = "";

	if ($name == "Tất cả") {
		$query = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, product_type.NAME, product.IMG FROM product join product_type on product.PRODUCT_TYPE = product_type.TYPE_ID";
	}
	else{
		$query = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, product_type.NAME, product.IMG FROM product join product_type on product.PRODUCT_TYPE = product_type.TYPE_ID WHERE product_type.NAME = '$name' ";
	}

	
	$result = $conn->query($query);

	$array_product = array();
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			array_push($array_product, new Product($row['PRODUCT_ID'],$row['PRODUCT_NAME'],$row['PRODUCT_PRICE'],$row['NAME'],$row['IMG'])); 
		}
	}
	
	echo json_encode($array_product);

	class Product{
		function Product($product_id,$product_name,$product_price,$product_type,$product_img){
			$this->product_id = $product_id;
			$this->product_name = $product_name;
			$this->product_price = $product_price;
			$this->product_type = $product_type;
			$this->product_img = $product_img;
		}
	}

	$conn->close();

 ?>