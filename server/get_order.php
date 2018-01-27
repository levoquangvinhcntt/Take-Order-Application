<?php 
	include "connect.php";

	$query = "SELECT ID, TABLE_ID, KHACHHANG, TONGCONG FROM sale_order";
	$result = $conn->query($query);

	$array_order = array();
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$temp = "ID: ".$row['ID']. ' - KH: '. $row['KHACHHANG']. ' - Bàn: '. $row['TABLE_ID']. ' - Total: '. $row['TONGCONG'];
			array_push($array_order, new Table($temp)); 
		}
	}
	
	echo json_encode($array_order);

	class Table{
		function Table($content){
			$this->content = $content;
		}
	}

	$conn->close();


 ?>