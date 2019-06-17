<?php
function save_data() {
	global $con, $hitId, $workerId, $assignmentId, $esc, $subtasknumber;
	$data = $_REQUEST['data'];

	$sql = <<<SQL
    	insert into sampledata values ( "{$esc($assignmentId)}", "{$esc($hitId)}", "{$esc($workerId)}" , "{$esc($subtasknumber)}", "{$esc($data)}" );
SQL;
	if (!$con->query($sql) === TRUE) {
	    $sql = <<<SQL
    	insert into sampledata2 values ( "{$esc($assignmentId)}", "{$esc($hitId)}", "{$esc($workerId)}" , "{$esc($subtasknumber)}", "{$esc($data)}" );
SQL;
	$con->query($sql);
	}

}
try{
	
	$assignmentId = $_REQUEST['assignmentId'];
	$workerId = $_REQUEST['workerId'];
	$hitId = $_REQUEST['hitId'];
	$databasename = 'editorexperiment';
	$submit_time = date("Y-m-d H:i:s");
	$strikes = $_REQUEST['strikes'];
	$strikesFreq = $_REQUEST['strikesfreq'];
	$subtasknumber = $_REQUEST['stn'];
	require "utility.php";

	$con = get_mysql_connection($databasename);

	if(!verify_blacklist()) throw new Exception('ERROR (A21): Apologies, but you are not allowed to complete any hits in this batch.');
	log_submit();
	save_data(); 
	$response->success = 1;
	$response->message = "";
	$myJSON = json_encode($response);
	echo $myJSON;
}
catch (Exception $e) {
	$response->success = 0;
	$response->message = $e->getMessage();
	$myJSON = json_encode($response);
	echo $myJSON;
} finally {
	$con->close();
}
?>
