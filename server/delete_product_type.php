<?php 
	include "connect.php";
	$id = $_POST['id'];
	$query = "DELETE FROM product_type WHERE TYPE_ID = $id";
	if ($conn->query($query) == TRUE) {
    	echo "true";
	} else {
	    echo "Error: " . $query . "<br>" . $conn->error;
	}

	$conn->close();


 ?>