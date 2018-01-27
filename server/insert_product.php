<?php 
	include "connect.php";
	$id = 1;
	$name = $_POST['name'];
	$price = $_POST['price'];
	$img = $_POST['img'];
	$type = $_POST['type'];


	$query ="SELECT MAX(PRODUCT_ID) AS 'ID' FROM product ";
	$result = $conn->query($query);
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$id = $row['ID']+1;
		}
	}

	$query = "SELECT  TYPE_ID FROM product_type WHERE NAME ='$type'";
	$result = "";
	$result = $conn->query($query);
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$type = $row['TYPE_ID'];
		}
	}
	
	$sql_insert = "INSERT INTO product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_TYPE, IMG) VALUES ($id,'$name',$price,$type,'$img')";
	if ($conn->query($sql_insert) === TRUE) {
    	echo true;
	} else {
    	echo "Error: " . $sql_insert . "<br>" . $conn->error;
	}


 ?>