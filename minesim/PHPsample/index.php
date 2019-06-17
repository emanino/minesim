<?php 
$sandbox = False;

// <-- Remove in production
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$sandbox = True; 
// -->

require "utility.php";

$databasename = 'editorexperiment';
$assignmentId = $_GET["assignmentId"];
$hitId = $_GET["hitId"];
$workerId = $_GET["workerId"];
$turkSubmitTo = $_GET["turkSubmitTo"];
$subtasknumber = $_GET["stn"];
?>
<!DOCTYPE html>
<html>

<head>
<title>External Task</title>
<noscript><h1 style="color:red;">Error: the task requires Javascript! You will not be able to complete the task.</h1><h2 style="color:red;">Please reload this page with a Javascript enabled browser.</h2></noscript>
<script>function gettasknumber() { return "<?php echo($subtasknumber)?>";}</script>
<script>function isFormDisabled() { return <?php echo($assignmentId == "ASSIGNMENT_ID_NOT_AVAILABLE")?>;}</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
function post_submit(){
	var url = "<?php echo(get_base_dir()); ?>post.php";
	var aid = "<?php echo($assignmentId); ?>";
	var wid = "<?php echo($workerId); ?>";
	var hid = "<?php echo($hitId); ?>";
	var strike_num = getBolts();
	var strike_string = getBoltsString();
	var jsondata = JSON.stringify(solution);
	var posting = $.post( url, { assignmentId: aid, workerId: wid, hitId: hid, data: jsondata, strikes: strike_num, strikesfreq: strike_string, stn: "<?php echo($subtasknumber)?>" }, "json" );
	  posting.done(function( data ) {
		data = JSON.parse(data);
		if(data.success == 1){
			cont("");
		} else {
			$("body").html('<h1 style="color:red;">' + data.message + ' ('+ data.success +')</h1>');
		}	    
	  });
		
	}

function createHiddenField(key,value){
	var field = $('<input></input>');
	field.attr("type", "hidden");
        field.attr("name", key);
        field.attr("value", value);
	//var hiddenField = document.createElement("input");
	//hiddenField.setAttribute("type", "hidden");
	//hiddenField.setAttribute("name", key);
	//hiddenField.setAttribute("value", value);
	return field;
}
function cont(data){
	var url = "<?php echo($sandbox ? 'https://workersandbox.mturk.com/mturk/externalSubmit' : 'https://www.mturk.com/mturk/externalSubmit') ?>";
	var aid = "<?php echo($assignmentId); ?>";
	var wid = "<?php echo($workerId); ?>";
	var hid = "<?php echo($hitId); ?>";
	var form = $('<form></form>');
    	form.attr("method", "post");
    	form.attr("action", url);
	form.append(createHiddenField("assignmentId", aid));
	form.append(createHiddenField("workerId", wid));
	form.append(createHiddenField("hitId", hid));
	form.append(createHiddenField("datastring", data));
	$(document.body).append(form);
	form.submit();
}

</script>
</head>

<body>
<?php



try{
	
	$start_time = date("Y-m-d H:i:s");
	
	$preview = False;
	if($assignmentId == "ASSIGNMENT_ID_NOT_AVAILABLE") $preview = True;
	
	$con = get_mysql_connection($databasename);
	if (!$preview) if(!isset($_GET['assignmentId']) || !isset($_GET['hitId']) || !isset($_GET['workerId'])) throw new Exception('ERROR (A1): you are not authenticated to view this page.');
	if(!verify_blacklist()) throw new Exception('ERROR (A2): Apologies, but you are not allowed to complete any hits in this batch.');
	if(!$preview) initial_log();	
	require "Editor.html";
}
catch (Exception $e) {
	echo('<h1 style="color:red;">' . $e->getMessage() . '</h1>');
} finally {
	$con->close();
}
?>
</body>
</html>
