<?php 
	include "connect.php";
	$name = $_POST['name'];
	$img =$_POST['img'];
	$query = "SELECT  MAX(TYPE_ID) AS 'ID' FROM product_type ";
	$result = $conn->query($query);
	$id = 0;
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$id = $row['ID']+1;
		}
	}

	$sql_insert = "INSERT INTO product_type(TYPE_ID, NAME, IMG) VALUES ($id,'$name','$img')";

	if ($conn->query($sql_insert) === TRUE) {
    	echo true;
	} else {
    	echo "Error: " . $sql_insert . "<br>" . $conn->error;
	}

	$conn->close();

 ?>