
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mine Simulator - Mine Viewer</title>

<link rel="stylesheet" type="text/css" href="css/mine-style.css" media="screen" />
<script src="//code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="css/jquery-ui.min.css">
<script src="js/jquery-ui.min.js"></script>
<script src="js/draw-mine.js" type="text/javascript"></script>
<script src="js/app-ajax.js" type="text/javascript"></script>


</head>

<body>
    <h1>Mine Simulator</h1>
    
    
<div id="mainaccordion">
  <h3>Mine Generation</h3>
  <div>
    <p>
	<fieldset>
		<legend>Seed generation</legend>
		<button class="ui-button ui-widget ui-corner-all changeButtons" id="renderButton" >Generate Mine</button>
		Mine Seed: <input type="text" id="mineSeedForm" value="1"/>
		Update Seed: <input type="text" id="updateSeedForm" value="1"/>
	</fieldset>
	<fieldset>
		<legend>Random generation</legend>
		<button class="ui-button ui-widget ui-corner-all changeButtons" id="renderButtonRandom" >Generate Random Mine</button>
	</fieldset>
    </p>
  </div>
  <h3>Mine Update</h3>
  <div>
    <p>
	<fieldset>
		<legend>Perform one action a number of times:</legend>
		Times: <input type="text" id="updateActionNumber" value="1"/>
	    <button class="ui-button ui-widget ui-corner-all changeButtons" id="actionBusinessAsUsual" >Business As Usual</button>
		<button class="ui-button ui-widget ui-corner-all changeButtons" id="actionEvacuateMine" >Evacuate the mine</button>
		<button class="ui-button ui-widget ui-corner-all changeButtons" id="actionRefresh" >Refresh</button>
	</fieldset>
	<fieldset>
		<legend>Perform automatic updates / refresh visualisation:</legend>
		Milliseconds: <input type="text" id="updateActionMilliseconds" value="100"/>
		<label for="checkboxAutoRun">Automatic Update</label>
        <input type="checkbox" name="checkboxAutoRun" id="checkboxAutoRun" />
		<button class="ui-button ui-widget ui-corner-all changeButtons" id="actionRefresh" >Refresh</button>
	</fieldset>
    </p>
    
    <p>
    Action history:
	<table id="actionHistoryTable">
	    <tbody class='scrollable'>
      
	    </tbody>
	</table>
	</p>

  </div>
</div>    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

	

	

	
	<div id="drawArea">
	<strong>Current state of the mine:</strong>:
	<div id="ajaxGetUserServletResponse"></div>
	
	<svg id="mineSvg" width="1000" height="1000" viewBox="-40 -50 80 70" version="1.1" xmlns="http://www.w3.org/2000/svg" >
	</svg>
	<div style="font-size:75%">Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
	
	</div>

	
</body>
</html>
