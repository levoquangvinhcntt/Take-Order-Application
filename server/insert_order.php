<?php 
	include "connect.php";
	$id = 1;
	$khachhang = $_POST['khachhang'];
	$table = $_POST['table'];
	$content = $_POST['content'];
	$total = $_POST['total'];


	$query ="SELECT MAX(ID) AS 'ID' FROM sale_order";
	$result = $conn->query($query);
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_assoc()) {
			$id = $row['ID']+1;
		}
	}

	
	$sql_insert = "INSERT INTO sale_order(ID, TABLE_ID, KHACHHANG, TONGCONG, NOIDUNG) VALUES ($id,'$table','$khachhang','$total','$content')";
	
	if ($conn->query($sql_insert) === TRUE) {
    	echo true;
	} else {
    	echo "Error: " . $sql_insert . "<br>" . $conn->error;
	}




 ?>