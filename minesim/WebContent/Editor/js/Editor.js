var jsonData = [];
var solution;

function jsonLoaded(json){
	jsonData = jsonData.concat(json);
}


/**
 * 
 */
$( function() {
	
	// resolve bootrstrap conflicts with Jquery UI 
	var bootstrapButton = $.fn.button.noConflict();
	$.fn.bootstrapBtn = bootstrapButton;
	
	
	$.getJSON("js/predicates.json", function(json) {
		jsonLoaded(json); // this will show the info it in firebug console
	});
	$.getJSON("js/actions.json", function(json) {
		jsonLoaded(json); // this will show the info it in firebug console
	});
	
	refresh();
	var $item = $('.carousel-item'); 
	var $wHeight = $(window).height();
	$item.eq(0).addClass('active');
	$item.height($wHeight); 
	$item.addClass('full-screen');
	$('.carousel img').each(function() {
	  var $src = $(this).attr('src');
	  $(this).parent().css({
	    'background-image' : 'url(' + $src + ')',
	    'background-color' : '#4286f4'
	  });
	  $(this).remove();
	});

	$('.carousel').carousel({
		interval: false,
		keyboard: true,
		wrap: false
	});
	$('.carousel-control-prev').hide();
	$('.carousel').on('slid.bs.carousel', function () {
		    $('.carousel-control-prev').show();
		    $('.carousel-control-next').show();
		  if($('.carousel-indicators li:first').hasClass('active')) {
		    $('.carousel-control-prev').hide();
		  } else if($('.carousel-indicators li:last').hasClass('active')) {
		    $('.carousel-control-prev').hide();
		    $('.carousel-control-next').hide();
		  }
	});
	
  $( 'body' ).on('click', '.delbutton', function() {
  	$(this).parent().parent().remove();
  });
  $( 'body' ).on('mouseover', '.draggable', function() {
  	$(this).addClass('ui-state-highlight');
  	$(this).removeClass('ui-state-default');
  });
  $( 'body' ).on('mouseout', '.draggable', function() {
	  $(this).addClass('ui-state-default');
	  $(this).removeClass('ui-state-highlight');
  });
  $( 'body' ).on('mouseup', '.draggable', function() {
	  	if($(this).parent().attr('id') == "sortable"){
	  		$(this).find("select").prop('disabled', false);
	  	}
	  });
  
  $( 'body' ).on('mouseover', '.placeholder-area', function() {
  	if($("#sortable li").length == 0){
  		$("#placeholdertext").show();
  	} else {
  		$("#placeholdertext").hide();
  	}
  });
  $( 'body' ).on('mouseout', '.placeholder-area', function() {
  	if($("#sortable li").length == 0){
  		$("#placeholdertext").show();
  	} else {
  		$("#placeholdertext").hide();
  	}
  });
  $( 'body' ).on('click', '.variableselector', function() {
	  if($(this).find(":selected").css('color') == "rgb(255, 255, 255)" || $(this).find(":selected").css('color') == "white")
		  $(this).css('color', "rgb(0, 0, 0)");
	  else
		  $(this).css('color', $(this).find(":selected").css('color'));
	  if($(this).val() == "?"){
		  $(this).val(' ');
		  $("select").removeClass("selectedselectmenu");
		  $("entity-span").removeClass("selectedselectmenu");
		  $(this).addClass("selectedselectmenu");
		  $( "#dialog-form" ).dialog( "open" );
		  refreshEntityCreationDialog();
	  }
  	});
  $( 'body' ).on('click', '.entity-span', function() {
	  $("select").removeClass("selectedselectmenu");
	  $("entity-span").removeClass("selectedselectmenu");
	  $(this).addClass("selectedselectmenu");
	  $( "#dialog-form" ).dialog( "open" );
	  refreshEntityCreationDialog();
  });
  $("#search-button").button( {
      icon: "ui-icon-search",
      showLabel: false
    } );
  $("#search-button").click(function() {
  	search();
  });
  $("#info-button").button({icon: "ui-icon-info"});
  $("#info-button").click(function() {
	  	$(".carousel").carousel(0); 
	  });
  $("#sub-button").button({icon: "ui-icon-check"});
  $("#sub-button").click(function() {
	  solution = getJsonResult();
	  if (solution != null){
		  post_submit();
	  }
	  });
  
  
  $('#searchfield').keypress(function (e) {
  	  if (e.which == 13) {
  		search();
  	    return false;
  	  }
  	});
  
  valuefield = $( "#value-field-id" );
  typefield = $( "#entitytypeselect" );
  allFields = $( [] ).add( valuefield ).add( typefield );
  dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 250,
      width: 350,
      modal: true,
      buttons: {
        "Create": addEntity,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
  form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      addEntity();
    });

  $("#entitytypeselect").selectmenu({
	  change: function( event, ui ) {
		  refreshEntityCreationDialog();
		  if($("#entitytypeselect").val() == "http://www.w3.org/2001/XMLSchema#decimal" || $("#entitytypeselect").val() == "http://www.w3.org/2001/XMLSchema#string"){
			  $("#value-field-block").show();
		  }
	  }
  });
  refreshEntityCreationDialog();
});

function refreshEntityCreationDialog(){
  $(".hiddenDialogFields").hide();
  $("#value-field-id").uitooltip();
  $("#value-field-id").uitooltip('disable');
}

function checkSelect() {
	if($("#entitytypeselect").val().trim().length < 1){
		$("#entitytypeselect").addClass( "ui-state-error" );
		return false;
	} else {
		$("#entitytypeselect").removeClass( "ui-state-error" );
		return true;
	}
}
function checkVal() {
	if(validateType($("#value-field-id").val(),$("#entitytypeselect").val())){			
		$("#value-field-id").removeClass( "ui-state-error" );
		return true;
	}	
	$("#value-field-id").addClass( "ui-state-error" );
	return false;
}
function validateType(val,type){
	if($("#value-field-id").val().trim().length <= 0){
		$('#value-field-id').prop('title', ' ');
		//$("#value-field-id").uitooltip( "option", "disabled", false );
		$("#value-field-id").uitooltip({
			  content: "This field cannot be empty."
		});
		$("#value-field-id").uitooltip( "open" );
		return false;
	}
	if(type == "http://www.w3.org/2001/XMLSchema#decimal" && isNaN(val)) {
		$('#value-field-id').prop('title', ' ');
		//$("#value-field-id").uitooltip( "option", "disabled", false );
		$("#value-field-id").uitooltip({
			  content: "Please enter a valid number."
		});
		$("#value-field-id").uitooltip( "open" );
		return false;
	}
	return true;
}
function addEntity() {
    var valid = true;
    valid = valid && checkSelect();
    if($("#entitytypeselect").val() == "http://www.w3.org/2001/XMLSchema#decimal" || $("#entitytypeselect").val() == "http://www.w3.org/2001/XMLSchema#string"){
    	valid = valid && checkVal();
    }
    if(valid) {
    	if($("#entitytypeselect").val() == "variable"){
    		$(".selectedselectmenu").replaceWith($(getVariableSelectionHelper(false)));
    	} else {    		
    		$(".selectedselectmenu").replaceWith($('<span class="entity-span"><span class="entity-span-val">'+$("#value-field-id").val()+'</span><span class="entity-span-type">'+$("#entitytypeselect").val()+"</span></span>"));
    	}
    	$( "#dialog-form" ).dialog( "close" );
    }
    return valid;
  }

function refresh(){
  $( "#sortable" ).sortable({
      revert: true,
      dropOnEmpty: true
    });
  $( ".draggable" ).draggable({
    connectToSortable: "#sortable",
    helper: "clone",
    revert: "invalid"
  });
      
  $( "ul, li" ).disableSelection();
  
  
/*  $('.variableselector').change(function () {
	  alert($(this).val());
	  val = $(this).val();
	  if(val == "X"){
		  $(this).addClass("varoptionred");		  
	  }
	  });*/
}

function compareScore(a,b) {
	  if (a.score > b.score)
	    return -1;
	  if (a.score < b.score)
	    return 1;
	  return 0;
	}

function search(){
	var keywords = $("#searchfield").val().split(" ");
	$("#searchlist").empty();
	objs = []
	for(index in jsonData){
		score = scoreSimilarity(keywords, jsonData[index])
		if(score >= 1){
			objs.push({"score": score, "data": index})
		}
	}
	addSpecialPredicates();
	objs.sort(compareScore);
	for(objI in objs){
		addPredicate(jsonData[objs[objI].data], objs[objI].score);		
	}
	//$(".varoption")
	/*$( 'body' ).on('click', '.variableselector', function() {
		$(this).css('color', $(this).find(":selected").css('color'));
	  });
	

	  $(".variableselector").click(function() {
		  $(this).css('color', $(this).find(":selected").css('color'));
		  });*/
	/*$(".variableselector").selectmenu({
		create: function() {
	        $( "#selectmenu" ).selectmenu( "widget" ).css( "color", $( "#selectmenu" ).val() );
	    },
	    select: function( event, data ) {
	        $( event.currentTarget.parentElement).css( "color", data.item.label );
	    }
	});
	$(".variableselector").selectmenu( "refresh" );*/
	refresh();	
}
var stopwords = ["a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"];

function scoreSimilarity(keywords, predicate){
	score = 0;
	score_cumulative = 0;
	cumulative_num = 0;
	for(labelI in predicate["label"]){
		if(isNaN(predicate["label"][labelI])){
			tokens = predicate["label"][labelI].split(" ");
			for(token in tokens){
				for(wordI in keywords){
					if(stopwords.indexOf(keywords[wordI].toLowerCase().trim()) < 0 && stopwords.indexOf(tokens[token].toLowerCase().trim()) < 0 ){						
						if(keywords[wordI].toLowerCase().trim() == tokens[token].toLowerCase().trim()) 
							score++;
						else {
							score_cumulative = score_cumulative + Math.pow(1/levenshteinDistance(keywords[wordI].toLowerCase().trim(), tokens[token].toLowerCase().trim()),3);
							cumulative_num++;
						}
					}
				} 
			}
		}
	}
	if(cumulative_num > 0)
		score = score + score_cumulative;
	return score;
}

function addPredicate(predicate, score){
	content = "";
	for (labelI in predicate["label"]){
		label = predicate["label"][labelI];
		if(isNaN(label)){
			content += label+" ";
		} else {
			content += '<span class="block-variable" var-num="'+label+'">'+getVariableSelection()+"</span>";
		}
	};
	$("#searchlist").append('  <li class="ui-state-default draglist draggable predicate-block" block-data="'+predicate["propertyName"]+'" block-var-num="'+predicate["propertyVariables"].length+'"><p class="alignleft">'+content+'</p><p class="alignright"><span class="ui-icon ui-icon-trash delbutton"></span></p><div style="clear: both;"></div></li>');
}

function addSpecialPredicates(){
	$("#searchlist").append('  <li class="ui-state-default draglist draggable connective-block" block-data="if"><p class="alignleft cursivebold">if</p><p class="alignright"><span class="ui-icon ui-icon-trash delbutton"></span></p><div style="clear: both;"></div></li>');
	$("#searchlist").append('  <li class="ui-state-default draglist draggable connective-block" block-data="then"><p class="alignleft cursivebold">then</p><p class="alignright"><span class="ui-icon ui-icon-trash delbutton"></span></p><div style="clear: both;"></div></li>');
}

function getVariableSelection(){
	return getVariableSelectionHelper(true);
}
function getVariableSelectionHelper(disabled){
	return '<select name="vars" class="variableselector" '+(disabled ? "disabled" : "")+'>'+
	'<option value=" " class="varoption"> </option>'+
	'<option value="A" class="varoption varoptionc1">A</option>'+
	'<option value="B" class="varoption varoptionc2">B</option>'+
	'<option value="C" class="varoption varoptionc3">C</option>'+
	'<option value="X" class="varoption varoptionc4">X</option>'+
	'<option value="Y" class="varoption varoptionc5">Y</option>'+
	'<option value="Z" class="varoption varoptionc6">Z</option>'+
	'<option value="W" class="varoption varoptionc7">W</option>'+
	'<option value="K" class="varoption varoptionc8">K</option>'+
	'<option value="?" class="varoption varoptionc0">[...]</option>'+
	'</select>';
}

function display_error(message,entity,delay){
	entity.prop('title', ' ');
	entity.addClass("ui-state-error");
	entity.uitooltip({
		  content: message
	});
	entity.uitooltip( "open" );
	setTimeout(function(){
			entity.uitooltip('disable').removeClass("ui-state-error");
		}, delay);
}

function getJsonResult(){
	emptyErrorMessage = "Warning: the solution is empty. Please drag and drop here the blocks that you think make the best solution.";
	try{
		if($("#sortable li").length < 1) throw emptyErrorMessage;
		data = {
				unassigned: [],
				if_block: [],
				then_block: []
		};
		scope = "";
		$("#sortable li").each(function( index ) {
			item = $(this);
			blockdata = item.attr('block-data');
			if(item.hasClass("connective-block")) 
				scope = blockdata;
			else if(item.hasClass("predicate-block")) {
				blockvarnum = item.attr('block-var-num');
				object = { 
						name: blockdata,
						variables: []
						}
				item.find(".block-variable").each(function( index ) {
					variable = $(this);
					varnumber = variable.attr("var-num");
					if(variable.has('select').length){
						// it is a variable field
						value = variable.find('select').val();
						if(value.trim().length <= 0) {
							display_error("Empty field!",$(this),5000);
							throw "Warning: not all fields have been chosen in your solution. Please choose a variable, or a number/text if required.";
						}
						object.variables.push({
							varnum: varnumber,
							type: "variable",
							val: value
						});
					} else {
						// it is a constant field
						value = variable.find("entity-span-val").text();
						lit_type = variable.find("entity-span-type").text();
						if(value.trim().length <= 0) throw "ERROR, not all entities have been initialised in your solution.";
						object.variables.push({
							varnum: varnumber,
							type: lit_type,
							val: value
						});
					}
				});
				if(object.variables.length < blockvarnum) throw "Error, a predicate does not have all the required variables.";
				if(scope == "if"){					
					if(data.then_block.length > 0) throw "Warning: you cannot put 'if' statements after 'then'. Please move your if statements before the 'then' block.";
					data.if_block.push(object);
				} else if(scope == "then"){
					if(data.if_block.length < 1) throw "Warning: you have statements after 'then' but there are no 'if' statements. To fix this, add the 'if' block and its conditions.";
					data.then_block.push(object);
				} else {
					data.unassigned.push(object);
				}
			}
		});
		if(data.if_block.length > 0 &&  data.then_block.length <= 0 && data.unassigned.length <= 0) 
			throw "Warning: you have statements after 'if', but there is no statement after 'then'.";
		if(data.if_block.length <= 0 &&  data.then_block.length <= 0 && data.unassigned.length <= 0) 
			throw emptyErrorMessage;
		return data;
	} catch(err) {
		display_error(err,$("#sortable"),10000);
		return null;
	}
}

/*
Copyright (c) 2011 Andrei Mackenzie
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

// Compute the edit distance between the two given strings
function levenshteinDistance(a, b){
  if(a.length == 0) return b.length; 
  if(b.length == 0) return a.length; 

  var matrix = [];

  // increment along the first column of each row
  var i;
  for(i = 0; i <= b.length; i++){
    matrix[i] = [i];
  }

  // increment each column in the first row
  var j;
  for(j = 0; j <= a.length; j++){
    matrix[0][j] = j;
  }

  // Fill in the rest of the matrix
  for(i = 1; i <= b.length; i++){
    for(j = 1; j <= a.length; j++){
      if(b.charAt(i-1) == a.charAt(j-1)){
        matrix[i][j] = matrix[i-1][j-1];
      } else {
        matrix[i][j] = Math.min(matrix[i-1][j-1] + 1, // substitution
                                Math.min(matrix[i][j-1] + 1, // insertion
                                         matrix[i-1][j] + 1)); // deletion
      }
    }
  }

  return matrix[b.length][a.length];
};