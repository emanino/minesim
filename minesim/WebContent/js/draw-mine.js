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

function drawMine(jsonMine) {
	
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
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "tunnel")
			addTunnel(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "sensor")
			addSensor(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "minerperson")
			addPerson(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "fireperson")
			addFirePerson(svg, objArray[i], scale);
	
	// create all the tunnels
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "fire")
			addFire(svg, objArray[i], scale);
	
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
	
	console.log(svg); // debug
}