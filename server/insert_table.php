<?php 
	include "connect.php";
	$name = $_POST['name'];
	$query = "SELECT MAX(TABLE_ID) AS 'ID' FROM table_";
	$result = $conn->query($query);
	$id = 0;
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$id = $row['ID']+1;
		}
	}

	$sql_insert = "INSERT INTO table_(TABLE_ID, TABLE_NAME) VALUES ($id,'$name')";

	if ($conn->query($sql_insert) === TRUE) {
    	echo true;
	} else {
    	echo "Error: " . $sql_insert . "<br>" . $conn->error;
	}

	$conn->close();

 ?>