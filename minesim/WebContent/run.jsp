<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/mine-style.css" media="screen" />
<script src="//code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="css/jquery-ui.min.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/run.css">
<script src="js/draw-mine.js" type="text/javascript"></script>
<script src="js/holder.js"></script>
<script src="js/run.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/app-ajax.js" type="text/javascript"></script>
<title>Mine Task</title>
</head>
<body>

<div id="tutorialdiv">
<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block w-100" src="holder.js/800x400?auto=yes&bg=4286f4&fg=555&text= &" alt="First slide1">
      <div class="carousel-caption d-none d-md-block">
    	<h1>Welcome!</h1>
			<ul>
			<li><span class="underline">Please take a few minutes to read through the instructions of this game</span>. It should take about 5 minutes.</li>
			<li>Use the arrows on this page to navigate through the instructions.</li>
			<li>On the last page, click on the button to start the tasks.</li>
			<li>After completing the tasks you will be given the code to complete the HIT and claim the payment.</li>
  	  		</ul>
  	  </div>
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="holder.js/800x400?auto=yes&bg=4286f4&fg=555&text= &" alt="First slide">
      <div class="carousel-caption d-none d-md-block">
    	<h5>Game instructions:</h5>
			<ul>
			<li>In this game, you play the role of a supervisor of a coal mine.</li>
			<li>Your job is to ensure health and safety by monitoring the working environment and taking the necessary actions to ensure health and safety.</li>
  	  		</ul>
  	  		
  	  		
  	  </div>
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="holder.js/800x400?auto=yes&bg=4286f4&fg=555&text= &" alt="First slide">
      <div class="carousel-caption d-none d-md-block">
      	<div class="leftcol">
    	<h5>Mine Layout:</h5>
			<ul>
			<li>This is an example mine layout:</li>
  	  		</ul>
  	  	</div>	
  	  </div>
	  <div class="carousel-caption d-none d-md-block">
  	  	<div class="rightcol">
  	  		<div id="mineVisualizationArea1"></div>
  	  	</div>
  	  </div>
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="holder.js/800x400?auto=yes&bg=4286f4&fg=444&text= &" alt="Second slide">
      <div class="carousel-caption d-none d-md-block">
    	<h5>In this game you only need to focus on <span class="underline">three types of danger</span>:</h5>
			<ul>
			<li><span class="bold">High temperatures</span>. Some areas of the mine might get too hot, making work there uncomfortable for workers or even dangerous.</li>
			<li><span class="bold">High level of carbon monoxide (CO)</span>. Sudden gas leaks or machinery gas emissions can cause a rise of CO gas concentration. This gas is very hazardous, and workers’ exposure to it should me minimized.</li>
			<li><span class="bold">Fire</span>. Fires in a mine are a very serious threat. They can cause harm by increasing temperature and releasing toxic gases. Moreover, they can spread and increase in severity if not contained.</li>
  	  		</ul>
  	  </div>
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="holder.js/800x400?auto=yes&bg=4286f4&fg=333&text= &" alt="Third slide">
      <div class="carousel-caption d-none d-md-block">
    	<h5>When deciding how to react to a danger, there are  <span class="underline">two additional factors to consider</span>:</h5>
			<ul>
			<li><span class="bold">Are there workers near the source of danger?</span> If a danger occurs in a deserted tunnel in the mine, it might be possible to deal with it without disrupting.</li>
			<li><span class="bold">Is the source of danger located in a main tunnel or in a side tunnel?</span>  The two main tunnels are the only exit routes in the mine. Dangerous situations in the main tunnels are particularly risky, as they might prevent workers from evacuating the mine, leaving them trapped inside.
</li>
			</ul>
  	  </div>
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
</div>
</body>
</html>