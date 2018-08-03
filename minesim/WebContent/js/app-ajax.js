var startDate;

$( function() {
	
		$("#drawArea").hide();
		$(".changeButtons").button();
		$( "#checkboxAutoRun" ).checkboxradio();
		$( "#mainaccordion" ).accordion();
		$( "#actionEvacuateTunnelSelection" ).selectmenu().selectmenu( "menuWidget" ).addClass( "overflow" );
		
		var maxWidth  = $('#inner').width();
		var maxHeight = $('#inner').height();
		$(window).resize(function(evt) {
		    var $window = $(window);
		    var width = $window.width();
		    var height = $window.height();
		    var scale;

		    // early exit
		    if(width >= maxWidth && height >= maxHeight) {
		        $('#inner').css({'-webkit-transform': ''});
		        $('#outer').css({ width: '', height: '' });
		        return;
		    }
		    
		    scale = Math.min(width/maxWidth, height/maxHeight);
		    
		    $('#inner').css({'-webkit-transform': 'scale(' + scale + ')'});
		    $('#outer').css({ width: maxWidth * scale, height: maxHeight * scale });
		});
		
		/*function doResize(event, ui) {
			var $el = $("#displayGrid");
			var elHeight = $el.outerHeight();
			var elWidth = $el.outerWidth();
			  var scale, origin;
			    
			  scale = Math.min(
			    ui.size.width / elWidth,    
			    ui.size.height / elHeight
			  );
		  $el.css({
		    transform: "translate(-50%, -50%) " + "scale(" + scale + ")"
		  });	  
			}
		$("#scaleable-wrapper").resizable({
			  resize: doResize
			});
		var starterData = { 
		  size: {
		    width: $("#scaleable-wrapper").width(),
		    height: $("#scaleable-wrapper").height()
		  }
		}
		doResize(null, starterData);*/
		
		
	    $( "#renderButton" ).click( function( event ) {
	      event.preventDefault();
	      disableButtons()
	      $.ajax({
				url : 'GetMineServlet',
				data : {
					mineSeed : $('#mineSeedForm').val(),
					updateSeed : $('#updateSeedForm').val()
				},
				success : function(responseText) {
					$("#drawArea").show();
					drawMine(responseText);
					completeFields(responseText);
					$("#actionEvacuateTunnelSelection").selectmenu( "refresh" );
					enableButtons()
				}
			});
	    } );
	    
	    $( "#renderButtonRandom" ).click( function( event ) {
		      event.preventDefault();
		      disableButtons()
		      $('#mineSeedForm').val(""+Math.floor(Math.random() * 99999999));
		      $('#updateSeedForm').val(""+Math.floor(Math.random() * 99999999));
		      $.ajax({
					url : 'GetMineServlet',
					data : {
						mineSeed : $('#mineSeedForm').val(),
						updateSeed : $('#updateSeedForm').val()
					},
					success : function(responseText) {
						$("#drawArea").show();
						drawMine(responseText);
						completeFields(responseText);
						enableButtons();
					}
				});
		    } );
	    $( "#actionBusinessAsUsual" ).click( function( event ) {
	    	disableButtons()
		    event.preventDefault();
		    addAction(0,parseInt($('#updateActionNumber').val()),"","Business as usual.");
		    redrawMine();
	    } );
	    $( "#actionEvacuateMine" ).click( function( event ) {
	    	disableButtons()
		    event.preventDefault();
		    addAction(1,parseInt($('#updateActionNumber').val()),"","Evacuate the mine.");
		    redrawMine();
	    } );
	    $( "#actionEvacuateTunnel" ).click( function( event ) {
	    	disableButtons()
		    event.preventDefault();
	    	var tunnelnum = $( "#actionEvacuateTunnelSelection" ).find('option:selected').val().substr(1);
		    addAction(2,parseInt($('#updateActionNumber').val()),tunnelnum,"Evacuate tunel T"+tunnelnum+".");
		    redrawMine();
	    } );
	    
	    $( "#actionRefresh" ).click( function( event ) {
	    	disableButtons()
		    event.preventDefault();
		    redrawMine();
	    } ); 
	    $('#checkboxAutoRun').change(function() {
	        if(this.checked) {
		    	disableButtons();
		    	$("#checkboxAutoRun").button( "option", "disabled", false );
			    setTimeout(periodicUpdate, $("#updateActionMilliseconds").val());
	        } else {
	        	enableButtons()
	        }   
	    });
	
});

function periodicUpdate(){
	if($('#checkboxAutoRun').is(':checked')){
		addAction(0,parseInt($('#updateActionNumber').val()),"","Business as usual.");
		redrawMine();
	}
	
}

var numUpdates = 0;	
function getActionHistoryNumUpdates(){
	var table = $("#actionHistoryTable");
	numUpdates = 0
	table.find('tr').each(function (i, el) {
		var $tds = $(this).find('td');
    	numUpdates = numUpdates+parseInt($tds.eq(1).text());
    });
	return numUpdates;
}

function readActionHistoryValues(){
	var table = $("#actionHistoryTable");
	var objects = {};
	table.find('tr').each(function (i, el) {
         var $tds = $(this).find('td'),
             num = $tds.eq(0).text(),
             times = $tds.eq(1).text(),
             code = $tds.eq(2).text(),
             params = $tds.eq(3).text();
         objects[num] = {};
         objects[num]["times"] = times;
         objects[num]["code"] = code;
         objects[num]["params"] = params;
         // do something with productId, product, Quantity
     });
	return objects;
}

// code: the type of action
// 0 - business as usual
// 1 - evacuate the mine
// times: the number of timesteps to repeat the action
// params: a comma separated string with any additional parameters
// text: a human readable label for the action

function addAction(code, times, params, text){
	var table = $("#actionHistoryTable");
	var addOne = false;
	table.find('tr:last').each(function (i, el) {
         var $tds = $(this).find('td'),
         	lastnum = $tds.eq(0).text(),
         	lasttimes = $tds.eq(1).text(),
         	lastcode = $tds.eq(2).text(),
         	lastparams = $tds.eq(3).text();
         if(code == lastcode && params == lastparams){
        	 $(this).find('td').eq(1).text(parseInt($(this).find('td').eq(1).text())+times);
        	 addOne = true;
         }
         // do something with productId, product, Quantity
     });	
	if(!addOne){
		if(table.find('tr').length == 0){
			table.find('tbody').prepend('<tr>'+
					'<td>'+((table.find('tr').length)+1)+'</td>'+
					'<td>'+times+'</td>'+
					'<td style="display:none">'+code+'</td>'+
					'<td style="display:none">'+params+'</td>'+
					'<td>'+text+'</td>'+
			'</tr>');
		} else {			
			table.find('tr:last').after('<tr>'+
					'<td>'+((table.find('tr').length)+1)+'</td>'+
					'<td>'+times+'</td>'+
					'<td style="display:none">'+code+'</td>'+
					'<td style="display:none">'+params+'</td>'+
					'<td>'+text+'</td>'+
			'</tr>');
		}
	}
}

function completeFields(jsonMineText){
	addTunnelsToMenus(jsonMineText);
}
function addTunnelsToMenus(jsonMine) {
	var objArray = jsonMine.mineObjects;
	$("#actionEvacuateTunnelSelection").empty();
	for(var i = 0; i < objArray.length; i++)
		if(objArray[i].type == "tunnel"){
			$("#actionEvacuateTunnelSelection").append("<option val=\""+(objArray[i].name.substr(1))+"\">"+objArray[i].name+"</option>");
		}
	//$("#actionEvacuateTunnelSelection").selectmenu( "refresh" );
}


function redrawMine(){
	startDate = new Date();
	$("#statRequestTime").html(""+startDate);
	$("#statRequestTimeMilliseconds").html(""+startDate.getTime());
	numUpdates = 
	$.ajax({
		url : 'GetMineUpdatedServlet',
		data : {
			mineSeed : $('#mineSeedForm').val(),
			updateSeed : $('#updateSeedForm').val(),
			jsonData : JSON.stringify(readActionHistoryValues())
		},
		success : function(responseText) {
			var currentDate = new Date();
			$("#statResponseTime").html(""+currentDate);
			$("#statResponseTimeMilliseconds").html(""+currentDate.getTime());
			var milliseconds = currentDate.getTime() - startDate.getTime();
			var updatesNum = getActionHistoryNumUpdates();
			var millisecondsPerUpdate = milliseconds/parseFloat(updatesNum);
			$("#statTotUpdates").html(""+updatesNum);
			$("#statTotMilliseconds").html(""+milliseconds);
			$("#statTotMillisecondsPerUpdate").html(""+Math.round(millisecondsPerUpdate));
			$("#statTotMillisecondsPerUpdateunrounded").html(""+millisecondsPerUpdate);
			$("#drawArea").show();
			drawMine(responseText);
			completeFields(responseText);
			if(!$('#checkboxAutoRun').is(':checked'))
				enableButtons();
			else
				setTimeout(periodicUpdate, $("#updateActionMilliseconds").val());
		}
	});
	
}

function disableButtons(){
	$(".changeButtons").button( "option", "disabled", true );
	$("#checkboxAutoRun").button( "option", "disabled", true );
	//$(".changeButtons").attr("disabled", "disabled");
}

function enableButtons(){
	$("#checkboxAutoRun").button( "option", "disabled", false );
	$(".changeButtons").button( "option", "disabled", false );
	//$(".changeButtons").removeAttr("disabled");
}














