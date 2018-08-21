function adjustViewBox(oldViewBox, objCoordinates) {
				
	var margin = 30;
	
	// unpack the coordinates
	var tokens = oldViewBox.split(" ");
	var minx = Number(tokens[0]);
	var miny = Number(tokens[1]);
	var width = Number(tokens[2]);
	var height = Number(tokens[3]);
	var objx = objCoordinates[0];
	var objy = objCoordinates[1];
	
	// fix x-axis
	if(objx < minx + margin)
		minx = objx - margin;
	else if(objx > minx + width - margin)
		width = objx - minx + margin;
	
	// fix y-axis
	if(objy < miny + margin)
		miny = objy - margin;
	else if(objy > miny + height - margin)
		height = objy - miny + margin;
	
	// pack the coordinates
	return [minx, miny, width, height].join(" ");
}
			
function addTunnel(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++) {
		object.c1[i] *= scale;
		object.c2[i] *= scale;
	}
	
	// tunnels are very thick lines with round edges
	var newTunnel = document.createElementNS("http://www.w3.org/2000/svg", 'line');
	newTunnel.setAttribute("x1", object.c1[0]);
	newTunnel.setAttribute("y1", object.c1[1]);
	newTunnel.setAttribute("x2", object.c2[0]);
	newTunnel.setAttribute("y2", object.c2[1]);
	newTunnel.setAttribute("style", "stroke:#FFFFFF;stroke-width:20;stroke-linecap:round");
	
	// and a label in their very middle
	var newTunnelName = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newTunnelName.setAttribute("x", (object.c1[0] + object.c2[0]) / 2);
	newTunnelName.setAttribute("y", (object.c1[1] + object.c2[1]) / 2 + 5);
	newTunnelName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#808080");
	newTunnelName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newTunnel);
	svg.appendChild(newTunnelName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c1);
	newViewBox = adjustViewBox(newViewBox, object.c2);
	svg.setAttribute("viewBox", newViewBox);
}

function addMainTunnel(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++) {
		object.c1[i] *= scale;
		object.c2[i] *= scale;
	}
	
	// tunnels are very thick lines with round edges
	var newTunnel = document.createElementNS("http://www.w3.org/2000/svg", 'line');
	newTunnel.setAttribute("x1", object.c1[0]);
	newTunnel.setAttribute("y1", object.c1[1]);
	newTunnel.setAttribute("x2", object.c2[0]);
	newTunnel.setAttribute("y2", object.c2[1]);
	newTunnel.setAttribute("style", "stroke:#C0C0C0;stroke-width:20;stroke-linecap:round");
	
	// and a label in their very middle
	var newTunnelName = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newTunnelName.setAttribute("x", (object.c1[0] + object.c2[0]) / 2);
	newTunnelName.setAttribute("y", (object.c1[1] + object.c2[1]) / 2 + 5);
	newTunnelName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#696969");
	newTunnelName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newTunnel);
	svg.appendChild(newTunnelName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c1);
	newViewBox = adjustViewBox(newViewBox, object.c2);
	svg.setAttribute("viewBox", newViewBox);
}

function addEscapeTunnel(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++) {
		object.c1[i] *= scale;
		object.c2[i] *= scale;
	}
	
	// tunnels are very thick lines with round edges
	var newTunnel = document.createElementNS("http://www.w3.org/2000/svg", 'line');
	newTunnel.setAttribute("x1", object.c1[0]);
	newTunnel.setAttribute("y1", object.c1[1]);
	newTunnel.setAttribute("x2", object.c2[0]);
	newTunnel.setAttribute("y2", object.c2[1]);
	newTunnel.setAttribute("style", "stroke:#C0C0C0;stroke-width:12;stroke-linecap:round");
	
	// and a label in their very middle
	var newTunnelName = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newTunnelName.setAttribute("x", (object.c1[0] + object.c2[0]) / 2);
	newTunnelName.setAttribute("y", (object.c1[1] + object.c2[1]) / 2 + 5);
	newTunnelName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#696969");
	newTunnelName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newTunnel);
	svg.appendChild(newTunnelName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c1);
	newViewBox = adjustViewBox(newViewBox, object.c2);
	svg.setAttribute("viewBox", newViewBox);
}

function addPerson(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// people are images
	var newPerson = document.createElementNS("http://www.w3.org/2000/svg", 'image');
	newPerson.setAttribute("x", object.c[0] - 10);
	newPerson.setAttribute("y", object.c[1] - 10);
	newPerson.setAttribute("width", 20);
	newPerson.setAttribute("height", 20);
	newPerson.setAttribute("href", "icons/person_icon.svg");
	
	// add their names
	var newPersonName = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newPersonName.setAttribute("x", object.c[0]);
	newPersonName.setAttribute("y", object.c[1] + 20);
	newPersonName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#000000");
	newPersonName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newPerson);
	svg.appendChild(newPersonName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addFirePerson(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// people are images
	var newPerson = document.createElementNS("http://www.w3.org/2000/svg", 'image');
	newPerson.setAttribute("x", object.c[0] - 10);
	newPerson.setAttribute("y", object.c[1] - 10);
	newPerson.setAttribute("width", 20);
	newPerson.setAttribute("height", 20);
	newPerson.setAttribute("href", "icons/fireperson_icon.svg");
	
	// add their names
	var newPersonName = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newPersonName.setAttribute("x", object.c[0]);
	newPersonName.setAttribute("y", object.c[1] + 20);
	newPersonName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#8B0000");
	newPersonName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newPerson);
	svg.appendChild(newPersonName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addFire(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// fires are images
	var newFire = document.createElementNS("http://www.w3.org/2000/svg", 'image');
	newFire.setAttribute("x", object.c[0] - 10);
	newFire.setAttribute("y", object.c[1] - 10);
	newFire.setAttribute("width", 20);
	newFire.setAttribute("height", 20);
	newFire.setAttribute("href", "icons/fire_icon.svg");
	
	// add everything to the parent element
	svg.appendChild(newFire);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addSensor(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// sensors are dots
	var newSensor = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
	newSensor.id = "svgElement"+object.name;
	newSensor.setAttribute("cx", object.c[0]);
	newSensor.setAttribute("cy", object.c[1]);
	newSensor.setAttribute("r", 5);
	newSensor.setAttribute("fill", "#00CED1");
	
	// add a value box
	var newSensorBox = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	newSensorBox.setAttribute("x", object.c[0] - 10);
	newSensorBox.setAttribute("y", object.c[1] + 8);
	newSensorBox.setAttribute("width", 20);
	newSensorBox.setAttribute("height", 15);
	newSensorBox.setAttribute("style", "fill:#FFFFFF;stroke:#000000;stroke-width:1");
	
	// add their readings
	var newSensorValue = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newSensorValue.setAttribute("x", object.c[0]);
	newSensorValue.setAttribute("y", object.c[1] + 20);
	newSensorValue.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#000000");
	newSensorValue.textContent = object.reading;
	
	// add everything to the parent element
	svg.appendChild(newSensor);
	svg.appendChild(newSensorBox);
	svg.appendChild(newSensorValue);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addHiddenSensor(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// sensors are dots
	var newSensor = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
	newSensor.id = "svgElement"+object.name;
	newSensor.setAttribute("cx", object.c[0]);
	newSensor.setAttribute("cy", object.c[1]);
	newSensor.setAttribute("r", 5);
	newSensor.setAttribute("fill", "#00CED1");
	
	// add a value box
	var newSensorBox = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	newSensorBox.id = "svgSensorBox"+object.name;
	newSensorBox.setAttribute("visibility", "hidden");
	newSensorBox.setAttribute("x", object.c[0] - 10);
	newSensorBox.setAttribute("y", object.c[1] + 8);
	newSensorBox.setAttribute("width", 20);
	newSensorBox.setAttribute("height", 15);
	newSensorBox.setAttribute("style", "fill:#FFFFFF;stroke:#000000;stroke-width:1");
	
	// add their readings
	var newSensorValue = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newSensorValue.id = "svgSensorReading"+object.name;
	newSensorValue.setAttribute("visibility", "hidden");
	newSensorValue.setAttribute("x", object.c[0]);
	newSensorValue.setAttribute("y", object.c[1] + 20);
	newSensorValue.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#000000");
	newSensorValue.textContent = object.reading;
	
	// add everything to the parent element
	svg.appendChild(newSensor);
	svg.appendChild(newSensorBox);
	svg.appendChild(newSensorValue);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addInfoPredicate(svg, object, scale){
	var predicateName = object.predicateName;
	var nameCode = predicateName.replace(/\s/g,'');
	var data = object.data;
	if($("#"+nameCode).length == 0){
		$("#tableArea").append("<h3>"+predicateName+"</h3><div>" +
				"<table id=\""+nameCode+"\" class=\"dataInfoTable hoverTable scrollable\"></table>" +
				"</div>");
	}
	var tableRow = "<tr>";
	for (var i = 0; i < data.length; i++) {
		tableRow = tableRow+"<td class=\"tableInfoClass"+data[i].type+"\">"+data[i].value+"</td>";
	}
	$("#"+nameCode).append(tableRow+"</tr>");
}

function highlightSensor(sensorId, turnOn){
	if(turnOn){
		$("#svgElement"+sensorId).attr("r", 40);
		$("#svgElement"+sensorId).attr("fill", "none");
		$("#svgElement"+sensorId).attr("stroke", "yellow");
		$("#svgElement"+sensorId).attr("stroke-width", "10");
		$("#svgSensorBox"+sensorId).attr("visibility","visible");
		$("#svgSensorReading"+sensorId).attr("visibility","visible");
	} else {		
		$("#svgElement"+sensorId).attr("r", 5);
		$("#svgElement"+sensorId).attr("fill", "#00CED1");
		$("#svgElement"+sensorId).attr("stroke", "none");
		$("#svgSensorBox"+sensorId).attr("visibility","hidden");
		$("#svgSensorReading"+sensorId).attr("visibility","hidden");
	}	
}

function addMiningSite(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// fires are images
	var newSite = document.createElementNS("http://www.w3.org/2000/svg", 'image');
	newSite.setAttribute("x", object.c[0] - 10);
	newSite.setAttribute("y", object.c[1] - 10);
	newSite.setAttribute("width", 20);
	newSite.setAttribute("height", 20);
	newSite.setAttribute("href", "icons/miningsite_icon.svg");
	
	// add everything to the parent element
	svg.appendChild(newSite);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addGeofencedAtom(svg, object, scale) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	var len = 3;
	var line1 = document.createElementNS("http://www.w3.org/2000/svg", 'line');
	line1.setAttribute("x1", object.c[0]-len);
	line1.setAttribute("y1", object.c[1]-len);
	line1.setAttribute("x2", object.c[0]+len);
	line1.setAttribute("y2", object.c[1]+len);
	line1.setAttribute("style", "stroke:#FF0000;stroke-width:2;stroke-linecap:round");
	var line2 = document.createElementNS("http://www.w3.org/2000/svg", 'line');
	line2.setAttribute("x1", object.c[0]-len);
	line2.setAttribute("y1", object.c[1]+len);
	line2.setAttribute("x2", object.c[0]+len);
	line2.setAttribute("y2", object.c[1]-len);
	line2.setAttribute("style", "stroke:#FF0000;stroke-width:2;stroke-linecap:round");
	
	// add everything to the parent element
	svg.appendChild(line1);
	svg.appendChild(line2);
}


function drawMineFull(jsonMine) {
	
	var objArray = jsonMine.mineObjects;
	var svg = document.getElementById("mineSvg");
	$("#mineSvg").empty();
	// create the background
	var ground = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var sky = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var exit = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	svg.appendChild(ground);
	svg.appendChild(sky);
	svg.appendChild(exit);
	
	// scale the coordinates
	var scale = 20;
	
	// create the escape tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "escapetunnel")
			addEscapeTunnel(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "tunnel")
			addTunnel(svg, objArray[i], scale);
	
	// create the main tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "maintunnel")
			addMainTunnel(svg, objArray[i], scale);
	
	// create all the geofenced atoms
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "geofencedAtom")
			addGeofencedAtom(svg, objArray[i], scale);
	
	// create all the miningSites
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "site")
			addMiningSite(svg, objArray[i], scale);
	
	// create all the sensors
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "sensor")
			addSensor(svg, objArray[i], scale);
	
	// create all the minerPeople
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "minerperson")
			addPerson(svg, objArray[i], scale);
	
	// create all the firePeople
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "fireperson")
			addFirePerson(svg, objArray[i], scale);
	
	// create all the fires
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "fire")
			addFire(svg, objArray[i], scale);
	
	// create all the infoPredicateTables
	$("#gtablediv").html("<div id=\"tableArea\"></div>");
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "infoPredicate")
			addInfoPredicate(svg, objArray[i], scale);
	$("#tableArea").accordion();
	
	
	// fit the background to the viewBox
	var tokens = svg.getAttribute("viewBox").split(" ");
	ground.setAttribute("x", tokens[0]);
	ground.setAttribute("y", 0);
	ground.setAttribute("width", tokens[2]);
	ground.setAttribute("height", tokens[3] - tokens[1]);
	ground.setAttribute("style", "fill:#A0522D");
	sky.setAttribute("x", tokens[0]);
	sky.setAttribute("y", -50);
	sky.setAttribute("width", tokens[2]);
	sky.setAttribute("height", 50);
	sky.setAttribute("style", "fill:#B0C4DE");
	exit.setAttribute("x", -20);
	exit.setAttribute("y", -30);
	exit.setAttribute("width", 40);
	exit.setAttribute("height", 30);
	exit.setAttribute("fill", "#696969");
	
	// console.log(svg);
	console.log(objArray);
}

function drawMine(jsonMine) {
	if($("#checkbox-1-hideen-view").length > 0 && !$("#checkbox-1-hideen-view").prop('checked')){		
		drawMineFull(jsonMine);
		return;
	}
	
	var objArray = jsonMine.mineObjects;
	
	console.log(objArray);
	
	var svg = document.getElementById("mineSvg");
	$("#mineSvg").empty();
	// create the background
	var ground = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var sky = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var exit = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	svg.appendChild(ground);
	svg.appendChild(sky);
	svg.appendChild(exit);
	
	// scale the coordinates
	var scale = 20;
	
	// create the escape tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "escapetunnel")
			addEscapeTunnel(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "tunnel")
			addTunnel(svg, objArray[i], scale);
	
	// create the main tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "maintunnel")
			addMainTunnel(svg, objArray[i], scale);
	
	// create all the geofenced atoms
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "geofencedAtom")
			addGeofencedAtom(svg, objArray[i], scale);
	
	// create all the miningSites
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "site")
			addMiningSite(svg, objArray[i], scale);
	
	// create all the sensors
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "sensor")
			addHiddenSensor(svg, objArray[i], scale);
	
	// create all the infoPredicateTables
	$("#gtablediv").html("<div id=\"tableArea\"></div>");
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "infoPredicate")
			addInfoPredicate(svg, objArray[i], scale);
	$("#tableArea").accordion();
	
	
	// fit the background to the viewBox
	var tokens = svg.getAttribute("viewBox").split(" ");
	ground.setAttribute("x", tokens[0]);
	ground.setAttribute("y", 0);
	ground.setAttribute("width", tokens[2]);
	ground.setAttribute("height", tokens[3] - tokens[1]);
	ground.setAttribute("style", "fill:#BC8F8F");
	sky.setAttribute("x", tokens[0]);
	sky.setAttribute("y", -50);
	sky.setAttribute("width", tokens[2]);
	sky.setAttribute("height", 50);
	sky.setAttribute("style", "fill:#B0C4DE");
	exit.setAttribute("x", -20);
	exit.setAttribute("y", -30);
	exit.setAttribute("width", 40);
	exit.setAttribute("height", 30);
	exit.setAttribute("fill", "#696969");
	
	// console.log(svg);
	console.log(objArray);
}
