<?php
	include 'db_helper.php';
	
	function listFavorites() {
		$dbQuery = sprintf("SELECT * FROM `favorites`");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}

	function listSchedule() {
		$dbQuery = sprintf("SELECT * FROM `schedule`");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}

	function listUsers() {
		$dbQuery = sprintf("SELECT `first_name` FROM `users`");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}




	function listTimes() {
		$dbQuery = sprintf("SELECT id,time FROM comments");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}

	function listComments() {
		$dbQuery = sprintf("SELECT id,comment,time FROM comments");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function getComment($id) {
		$dbQuery = sprintf("SELECT id,comment FROM comments WHERE id = '%s'",
			mysql_real_escape_string($id));
		$result=getDBResultRecord($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function addComment($comment) {
		$dbQuery = sprintf("INSERT INTO comments (comment) VALUES ('%s')",
			mysql_real_escape_string($comment));
	
		$result = getDBResultInserted($dbQuery,'personId');
		
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function updateComment($id,$comment) {
		$dbQuery = sprintf("UPDATE comments SET comment = '%s' WHERE id = '%s'",
			mysql_real_escape_string($comment),
			mysql_real_escape_string($id));
		
		$result = getDBResultAffected($dbQuery);
		
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function deleteComment($id) {
		$dbQuery = sprintf("DELETE FROM comments WHERE id = '%s'",
			mysql_real_escape_string($id));												
		$result = getDBResultAffected($dbQuery);
		
		header("Content-type: application/json");
		echo json_encode($result);
	}
?>