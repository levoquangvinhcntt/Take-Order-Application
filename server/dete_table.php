<?php 
	include "connect.php";
	$id = $_POST['id'];
	$query = "DELETE FROM table_ WHERE TABLE_ID = $id";
	if ($conn->query($query) == TRUE) {
    	echo "true";
	} else {
	    echo "Error: " . $query . "<br>" . $conn->error;
	}

	$conn->close();


 ?>