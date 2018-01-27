<?php 
	
	include "connect.php";

	$query = "SELECT TABLE_ID, TABLE_NAME FROM table_";
	$result = $conn->query($query);

	$array_table = array();
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			array_push($array_table, new Table($row['TABLE_ID'],$row['TABLE_NAME'])); 
		}
	}
	
	echo json_encode($array_table);

	class Table{
		function Table($table_id,$name){
			$this->table_id = $table_id;
			$this->name = $name;
		}
	}

	$conn->close();

 ?>