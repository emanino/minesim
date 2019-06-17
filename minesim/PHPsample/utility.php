<?php

$esc = "esc";
function esc($string){
	global $con;
	return mysqli_escape_string($con, $string);
}
function verify_worker_id_blacklist($id) {
	if(strlen($id) < 2)
		return True;
	global $con;
	$eid = esc($id);
	$sql = <<<SQL
	SELECT EXISTS(SELECT 1 FROM blacklist_worker_id WHERE worker_id = "$eid")
SQL;
	$results = $con->query($sql);
	if (mysqli_fetch_assoc($results)[0] == 1) return False;
	else return True;
}

function verify_blacklist() {
	global $con, $workerId;
	return verify_worker_id_blacklist($workerId);
}

function add_to_worker_id_blacklist($id) {
	global $con, $assignmentId, $hitId, $esc;
$sql = <<<SQL
    	insert ignore into blacklist_worker_id values ( "{$esc($id)}" , b"00" );
SQL;
	if (!$con->query($sql) === TRUE) {
	    throw new Exception('Apologies, something went wrong. Please contact us with the following error message: "ERROR B4-'.$hitId.'" and your worker id.');
	}
}
function blacklist() {
	global $workerId;
	add_to_worker_id_blacklist($workerId);
}

function log_identity() {
	global $con, $workerId, $assignmentId, $hitId, $esc;
	$sql = <<<SQL
    	insert ignore into identity values ( "{$esc($workerId)}" , "null" , "null" );
SQL;
	if (!$con->query($sql) === TRUE) {
	    throw new Exception('Apologies, something went wrong. Please contact us with the following error message: "ERROR B1-'.$hitId.'" and your worker id.');
	}
}

function log_start() {
	global $con, $workerId, $hitId,  $assignmentId, $start_time, $esc, $subtasknumber;
	$sql = <<<SQL
    	insert ignore into submission (assignment_id, hit_id, worker_id, start_time, stn) values ( "{$esc($assignmentId)}" , "{$esc($hitId)}" , "{$esc($workerId)}", "{$esc($start_time)}", "{$esc($subtasknumber)}" );  
SQL;
	if (!$con->query($sql) === TRUE) {
	    throw new Exception('Apologies, something went wrong. Please contact us with the following error message: "ERROR B2-'.$hitId.'" and your worker id.');
	}
}

function log_submit() {
	global $con, $workerId, $hitId, $assignmentId, $submit_time, $esc, $strikes, $strikesFreq;
	$sql = <<<SQL
    	UPDATE submission SET submit_time = "{$esc($submit_time)}", strikes = "{$esc($strikes)}", strikesFreq = "{$esc($strikesFreq)}" WHERE assignment_id = "{$esc($assignmentId)}" AND worker_id = "{$esc($workerId)}" AND hit_id = "{$esc($hitId)}";
SQL;
	if (!$con->query($sql) === TRUE) {
	    throw new Exception('Apologies, something went wrong. Please contact us with the following error message: "ERROR C1-'.$hitId.'" and your worker id.');
	}
}

function get_mysql_connection($db){
        $mysqli = new mysqli("yourserver", "yourusername", "yourpassword", "yourdatabase"); 
	// edit this and remove the following exception
	throw new Exception('CONFIGURATION ERROR: you need to provide credentials to connect to your database');

	if ($mysqli->connect_errno) {
    		throw new Exception('Please contact us quoting the following error "ERROR 051: database issues."');
	}
	return $mysqli;
}
function get_base_dir(){

	$url = $_SERVER['REQUEST_URI'];
	$parts = explode('/',$url);
	$dir = "";
	for ($i = 0; $i < count($parts) - 1; $i++) {
	 $dir .= $parts[$i] . "/";
	}
	return $dir;
}
function initial_log() {
	log_identity();
	log_start();
}
?>
