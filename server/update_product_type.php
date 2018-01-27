<?php 
	include "connect.php";
	$id = $_POST['id'];
	$name = $_POST['name'];
	$img =$_POST['img'];

	$query = "UPDATE product_type SET NAME = '$name', IMG = '$img' WHERE TYPE_ID = $id";

	if ($conn->query($query) == TRUE) {
    	echo "true";
	} else {
	    echo "Error: " . $query . "<br>" . $conn->error;
	}

	$conn->close();


 ?>