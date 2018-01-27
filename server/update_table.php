<?php 
	include "connect.php";
	$id = $_POST['id'];
	$name = $_POST['name'];

	$query = "UPDATE table_ SET TABLE_NAME = '$name' WHERE TABLE_ID = $id";

	if ($conn->query($query) == TRUE) {
    	echo "true";
	} else {
	    echo "Error: " . $query . "<br>" . $conn->error;
	}

	$conn->close();


 ?>