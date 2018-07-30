$(document).ready(function() {
	
		$("#drawArea").hide();
		$(".changeButtons").button();
		//alert($( "#mainaccordion" ));
		$( "#mainaccordion" ).accordion();
		
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
						enableButtons()
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
		       
	
});

	
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

function redrawMine(){
	$.ajax({
		url : 'GetMineUpdatedServlet',
		data : {
			mineSeed : $('#mineSeedForm').val(),
			updateSeed : $('#updateSeedForm').val(),
			jsonData : JSON.stringify(readActionHistoryValues())
		},
		success : function(responseText) {
			$("#drawArea").show();
			drawMine(responseText);
			enableButtons()
		}
	});
	
}

function disableButtons(){
	$(".changeButtons").button( "option", "disabled", true );
	//$(".changeButtons").attr("disabled", "disabled");
}

function enableButtons(){
	$(".changeButtons").button( "option", "disabled", false );
	//$(".changeButtons").removeAttr("disabled");
}














