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

// so far it only trims numbers and arrays of numbers
function trimNum(num){
	if(isNaN(num)) {
		if(num.constructor === Array){
			var newArray = [];
			for(elem in num){
				if(isNaN(num[elem])){
					return num;
				} else {
					newArray.push(trimNum(num[elem]));
				}			
				
			}
			return newArray;
		}
		return num;
	}
	return Math.round(num * 1000) / 1000;
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
	
	addClickableLabel("Tunnel: "+object.name,newTunnel, object.name, svg,(object.c1[0] + object.c2[0]) / 2,(object.c1[1] + object.c2[1]) / 2 + 5,
			object.c1[0]+"n"+object.c2[0], object.c1[1]+"n"+object.c2[1]);
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
	newTunnelName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#565656");
	newTunnelName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newTunnel);
	svg.appendChild(newTunnelName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c1);
	newViewBox = adjustViewBox(newViewBox, object.c2);
	svg.setAttribute("viewBox", newViewBox);
	
	addClickableLabel("Main Tunnel: "+object.name,newTunnel, object.name, svg,(object.c1[0] + object.c2[0]) / 2,(object.c1[1] + object.c2[1]) / 2 + 5,
			object.c1[0]+"n"+object.c2[0], object.c1[1]+"n"+object.c2[1]);
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
	newTunnelName.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;font-weight:bold;fill:#565656");
	newTunnelName.textContent = object.name;
	
	// add everything to the parent element
	svg.appendChild(newTunnel);
	svg.appendChild(newTunnelName);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c1);
	newViewBox = adjustViewBox(newViewBox, object.c2);
	svg.setAttribute("viewBox", newViewBox);
	
	addClickableLabel("Main Tunnel: "+object.name,newTunnel, object.name, svg,(object.c1[0] + object.c2[0]) / 2,(object.c1[1] + object.c2[1]) / 2 + 5,
			object.c1[0]+"n"+object.c2[0], object.c1[1]+"n"+object.c2[1]);
}


// When the svg 'parentObject', representation of 'object' is clicked, display a box showing 'text'
// in the 'svg' area at position x,y
// 'lx' and 'ly' are used to define a unique id for the box.
function addClickableLabel(text,parentObject, name,svg,x,y,lx,ly){
	var sensorDisplaySuffix = (lx.toString().replace(/\./g,'b'))+"a"+(ly.toString().replace(/\./g,'b'));
	parentObject.setAttribute("lx", lx);
	parentObject.setAttribute("ly", ly);
	parentObject.id = "svgElement"+name;
	parentObject.classList.add("clickableObject");
	
	if($("."+"labelValue"+sensorDisplaySuffix).length > 0){
		$("."+"labelValue"+sensorDisplaySuffix).text($("."+"labelValue"+sensorDisplaySuffix).text()+".\r\n "+text);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("x", x - $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().width/2-4);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("width", $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().width+8);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("height", $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().height);
	} else {	
		// add the text
		var newSensorValue = document.createElementNS("http://www.w3.org/2000/svg", 'text');
		newSensorValue.id = "svgElement"+name+"Reading";
		newSensorValue.setAttribute("x", x);
		newSensorValue.setAttribute("y", y + 20-33-12);
		newSensorValue.classList.add("normallyHidden");
		newSensorValue.classList.add("labelValue"+sensorDisplaySuffix);
		newSensorValue.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:18px;fill:#000000");
		//newSensorValue.setAttribute('style', 'white-space: pre-line;');
		newSensorValue.textContent = text;
		
		// add a value box
		var newSensorBox = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
		newSensorBox.id = "svgElement"+name+"Box";
		newSensorBox.classList.add("labelBox"+sensorDisplaySuffix);
		newSensorBox.setAttribute("y", y + 8-36-12);
		newSensorBox.classList.add("normallyHidden");
		newSensorBox.setAttribute("style", "fill:#FFFFFF;stroke:#000000;stroke-width:1");
		
		// add everything to the parent element
		svg.appendChild(newSensorBox);
		svg.appendChild(newSensorValue);
		newSensorBox.setAttribute("x", x - newSensorValue.getBBox().width/2-4);
		newSensorBox.setAttribute("width", newSensorValue.getBBox().width+8);
		newSensorBox.setAttribute("height", newSensorValue.getBBox().height);
	}
	
	
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

function addLeak(svg, object, scale, label) {
	
	// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// fires are images
	var newFire = document.createElementNS("http://www.w3.org/2000/svg", 'image');
	newFire.setAttribute("x", object.c[0] - 10);
	newFire.setAttribute("y", object.c[1] - 10);
	newFire.setAttribute("width", 20);
	newFire.setAttribute("height", 20);
	newFire.setAttribute("href", "icons/warning_icon.svg");
	
	// add the label
	var textlabel = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	textlabel.setAttribute("x", object.c[0]);
	textlabel.setAttribute("y", object.c[1] + 20);
	textlabel.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#8B0000");
	textlabel.textContent = label+" ("+object.size+")";
	
	// add everything to the parent element
	svg.appendChild(newFire);
	svg.appendChild(textlabel);
	
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
	newSensor.setAttribute("r", 7);
	newSensor.setAttribute("fill", "#00CED1");
	newSensor.classList.add("clickableObject");
	//newSensor.setAttribute("clickableObjectText", "Sensor "+object.name+" measured "+object.reading+" ("+object.propertyName+")");
	
	var textContent = object.propertyName+": "+trimNum(object.reading);
	addClickableLabel(textContent, newSensor, object.name, svg,
			object.c[0],object.c[1],
			object.c[0],object.c[1]);
	/*var sensorDisplaySuffix = (object.c[0].toString().replace(/\./g,'b'))+"a"+(object.c[1].toString().replace(/\./g,'b'));
	if($("."+"labelValue"+sensorDisplaySuffix).length > 0){
		$("."+"labelValue"+sensorDisplaySuffix).text($("."+"labelValue"+sensorDisplaySuffix).text()+".\r\n "+textContent);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("x", object.c[0] - $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().width/2);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("width", $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().width);
		$("."+"labelBox"+sensorDisplaySuffix)[0].setAttribute("height", $("."+"labelValue"+sensorDisplaySuffix)[0].getBBox().height);
	} else {
		// add their readings
		var newSensorValue = document.createElementNS("http://www.w3.org/2000/svg", 'text');
		newSensorValue.id = "svgElement"+object.name+"Reading";
		newSensorValue.setAttribute("x", object.c[0]);
		newSensorValue.setAttribute("y", object.c[1] + 20-30);
		newSensorValue.classList.add("normallyHidden");
		newSensorValue.classList.add("labelValue"+sensorDisplaySuffix);
		newSensorValue.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#000000");
		//newSensorValue.setAttribute('style', 'white-space: pre-line;');
		newSensorValue.textContent = textContent;
		
		// add a value box
		var newSensorBox = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
		newSensorBox.id = "svgElement"+object.name+"Box";
		newSensorBox.classList.add("labelBox"+sensorDisplaySuffix);
		newSensorBox.setAttribute("y", object.c[1] + 8-30);
		newSensorBox.classList.add("normallyHidden");
		newSensorBox.setAttribute("style", "fill:#FFFFFF;stroke:#000000;stroke-width:1");

		// add everything to the parent element
		svg.appendChild(newSensorBox);
		svg.appendChild(newSensorValue);
		newSensorBox.setAttribute("x", object.c[0] - newSensorValue.getBBox().width/2);
		newSensorBox.setAttribute("width", newSensorValue.getBBox().width);
		newSensorBox.setAttribute("height", newSensorValue.getBBox().height);
		
	}*/
	svg.appendChild(newSensor);
	
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);
}

function addHiddenSensor(svg, object, scale) {
	addSensor(svg, object, scale);
	/*// scale the coordinates
	for(var i = 0; i < 3; i++)
		object.c[i] *= scale;
	
	// sensors are dots
	var newSensor = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
	newSensor.id = "svgElement"+object.name;
	newSensor.setAttribute("cx", object.c[0]);
	newSensor.setAttribute("cy", object.c[1]);
	newSensor.setAttribute("r", 5);
	newSensor.setAttribute("fill", "#00CED1");
	newSensor.classList.add("clickableObject");
	newSensor.setAttribute("clickableObjectText", "Sensor "+object.name+" measured "+object.reading+" ("+object.propertyName+")");
	
	// add a value box
	var newSensorBox = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	newSensorBox.id = "svgSensorBox"+object.name;
	newSensorBox.setAttribute("x", object.c[0] - 10);
	newSensorBox.setAttribute("y", object.c[1] + 8);
	newSensorBox.setAttribute("width", 20);
	newSensorBox.setAttribute("height", 15);
	newSensorBox.setAttribute("style", "fill:#FFFFFF;stroke:#000000;stroke-width:1");
	
	// add their readings
	var newSensorValue = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	newSensorValue.id = "svgSensorReading"+object.name;
	newSensorValue.setAttribute("x", object.c[0]);
	newSensorValue.setAttribute("y", object.c[1] + 20);
	newSensorValue.setAttribute("style", "text-anchor:middle;font-family:arial;font-size:12px;fill:#000000");
	newSensorValue.textContent = object.reading;
	
	// add everything to the parent element
	svg.appendChild(newSensor);
	//svg.appendChild(newSensorBox);
	//svg.appendChild(newSensorValue);
	
	// adjust the size of the SVG viewBox
	var newViewBox = svg.getAttribute("viewBox");
	newViewBox = adjustViewBox(newViewBox, object.c);
	svg.setAttribute("viewBox", newViewBox);*/
}

function addInfoPredicate(svg, object, scale){
	var predicateName = object.predicateName;
	var nameCode = predicateName.replace(/\s/g,'');
	var data = object.data;
	if($("#"+nameCode).length == 0){
		$("#tableArea").append("<h3>"+predicateName+"</h3><div>" +
				"<table id=\""+nameCode+"\" class=\"dataInfoTable hoverTable scrollable\"></table>" +
				"</div>");
		var tableRow = "<tr>";
		for (var i = 0; i < data.length; i++) {
			tableRow = tableRow+"<td class=\"tableInfoHeader\">"+data[i].label+"</td>";
		}
		$("#"+nameCode).append(tableRow+"</tr>");
	}
	var tableRow = "<tr>";
	for (var i = 0; i < data.length; i++) {
		tableRow = tableRow+"<td class=\"tableInfoClass"+data[i].type+"\">"+trimNum(data[i].value)+"</td>";
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
	
	addClickableLabel("Mineral Deposit", newSite, object.name, svg,
			object.c[0] - 10,object.c[1] - 10,
			"m"+(object.c[0] - 10), "m"+(object.c[1] - 10));
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


function drawMineFull(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale) {
	
	var objArray = jsonMine.mineObjects;
	var svg = document.getElementById(mineSvgID);
	$("#"+mineSvgID).empty();
	// create the background
	var ground = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var sky = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	
	svg.appendChild(ground);
	svg.appendChild(sky);
	
	
	// scale the coordinates
	var scale = scale;
	
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
	
	var exit = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	svg.appendChild(exit);
	
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
	
	// create all the gasLeaks
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "gasleak")
			addLeak(svg, objArray[i], scale,"Gas Leak");
	
	// create all the temperatureincrease
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "tempincrease")
			addLeak(svg, objArray[i], scale, "Temperature Increase");
	
	// create all the infoPredicateTables
	$("#"+gtabledivID).html("<div id=\""+tableAreaID+"\"></div>");
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "infoPredicate")
			addInfoPredicate(svg, objArray[i], scale);
	$("#"+tableAreaID).accordion();
	
	
	// fit the background to the viewBox
	var tokens = svg.getAttribute("viewBox").split(" ");
	ground.setAttribute("x", tokens[0]);
	ground.setAttribute("y", 0);
	ground.setAttribute("width", tokens[2]);
	ground.setAttribute("height", tokens[3] - tokens[1]);
	ground.setAttribute("style", "fill:#ba7350");
	sky.setAttribute("x", tokens[0]);
	sky.setAttribute("y", -50);
	sky.setAttribute("width", tokens[2]);
	sky.setAttribute("height", 50);
	sky.setAttribute("style", "fill:#b6d3f9");
	exit.setAttribute("x", -20);
	exit.setAttribute("y", -30);
	exit.setAttribute("width", 40);
	exit.setAttribute("height", 30);
	exit.setAttribute("fill", "#696969");
	addClickableLabel("Mining Building on the Surface", exit, "miningsite", svg,
			0,0,
			"ext", "exit");
	
}


function drawMineHidden(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale) {
	var objArray = jsonMine.mineObjects;
	
	console.log(objArray);
	
	var svg = document.getElementById(mineSvgID);
	$("#"+mineSvgID).empty();
	// create the background
	var ground = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	var sky = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	
	svg.appendChild(ground);
	svg.appendChild(sky);
	
	
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
	
	var exit = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	svg.appendChild(exit);
	
	// create all the miningSites
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "site")
			addMiningSite(svg, objArray[i], scale);
	
	// create all the sensors
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "sensor")
			addHiddenSensor(svg, objArray[i], scale);
	
	// create all the infoPredicateTables
	$("#"+gtabledivID).html("<div id=\""+tableAreaID+"\"></div>");
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "infoPredicate")
			addInfoPredicate(svg, objArray[i], scale);
	$("#"+tableAreaID).accordion();
	
	
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
	sky.setAttribute("style", "fill:#b6d3f9");
	exit.setAttribute("x", -20);
	exit.setAttribute("y", -30);
	exit.setAttribute("width", 40);
	exit.setAttribute("height", 30);
	exit.setAttribute("fill", "#696969");
	addClickableLabel("Mining Building on the Surface", exit, "miningsite", svg,
			0,0,
			"ext", "exit");
}



function drawMine(jsonMine) {
	drawMineHelper(jsonMine, "mineSvg", "gtablediv", "tableArea", 20);
}

function drawMineHelper(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale) {
	if($("#checkbox-1-hideen-view").length > 0 && !$("#checkbox-1-hideen-view").prop('checked')){		
		drawMineFull(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale);
	} else {
		drawMineHidden(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale);
	}
}
function generateMineCanvas(spanID,jsonMine, mineSvgID, gtabledivID, tableAreaID, scale, full, idSuffix){
	$("#"+spanID).append(
	'<div id="wrapper'+idSuffix+'" class="wrapper wrapper-expl"><div id="outer'+idSuffix+'" class="outer outer-expl"><div id="inner'+idSuffix+'" class="inner inner-expl">'+
	'<div class="gdraw"><svg id="'+mineSvgID+'" width="750" height="650" viewBox="-40 -50 80 70" version="1.1" xmlns="http://www.w3.org/2000/svg" >'+
	'</svg>'+
	//'<div style="font-size:5%; text-align: center">Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>'+
	'</div></div></div></div></div>');
	if (full) drawMineFull(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale);
	else drawMineHidden(jsonMine, mineSvgID, gtabledivID, tableAreaID, scale);
}





