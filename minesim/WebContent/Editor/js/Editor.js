var jsonData = [];
var jsonSentences = [];
var solution;

/////////////////////////////////////
//function gettasknumber() {return "sample0";}; function isFormDisabled() {return false;};
/////////////////////////////////////

var bolts = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
var boltsFreq = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];

var starttime = new Date().getTime();

var isIF = false;
var mandatoryVars = null;

function getBolts(){
	var tot = 0;
	for(index in bolts)
		tot += bolts[index];
	return tot;
}

function getBoltsString(){
	return JSON.stringify(boltsFreq);
}

function jsonLoaded(json){
	jsonData = jsonData.concat(json);
}
function jsonSentencesLoaded(json){
	jsonSentences = jsonSentences.concat(json);
	for(index in jsonSentences){
		if(jsonSentences[index]["sentenceID"] == gettasknumber()){
			$("#main-sentence").html(jsonSentences[index]["sentence"]);			
			$("#small-main-sentence").html('"'+jsonSentences[index]["sentence"]+'"');
			isIF = jsonSentences[index]["isIF"] == 1;
			if(jsonSentences[index]["mandatoryVariables"].length > 0)
				mandatoryVars = jsonSentences[index]["mandatoryVariables"];
		}
		
	}
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
	$.getJSON("js/sentences.json", function(json) {
		jsonSentencesLoaded(json); // this will show the info it in firebug console
	});
	
	
	/////////////////////////////////////
	//jsonData = [];
	//$.getJSON("js/fakepredicates.json", function(json) {
	//	jsonLoaded(json); // this will show the info it in firebug console
	//});
	/////////////////////////////////////
	
	refresh();
	var $item = $('.carousel-item'); 
	var $wHeight = $(window).height();
	//restrict to 600px
	var $wHeight = "600px";
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
		    $('.carousel-indicators').show();
		  if($('.carousel-indicators li:first').hasClass('active')) {
		    $('.carousel-control-prev').hide();
		  } else if($('.carousel-indicators li:last').hasClass('active')) {
		    $('.carousel-control-prev').hide();
		    $('.carousel-indicators').hide();
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
  
  $( 'body' ).on('click', '#nosub-button', function() {
	  $( "#dialog-form2" ).dialog( "open" );
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
	  if (solution != null && checkTime()){
		  post_submit();
	  }
	  });
  $("#nosub-button").button({icon: "ui-icon-closethick"});
  
  
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

  dialog2 = $( "#dialog-form2" ).dialog({
      autoOpen: false,
      height: 350,
      width: 450,
      modal: true,
      buttons: {
        "Report impossible answer": submitNoAnswer,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
    	  $("#snippet-field").val("");
      }
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
  
  if(isFormDisabled()){
	  $("button").attr("disabled", "disabled");
  }
});

function submitNoAnswer(){
	solution = getNoJsonResult();
	  if (solution != null && checkTime() && verifyNoAnswer()){
		  post_submit();
	  } else {
		  if (solution == null){			  
			  bolts[9] = 3;
			  boltsFreq[9] += 1;
			  display_error("This field cannot be empty.",$("#snippet-field"),3000);
		  }
	  }
}

function verifyNoAnswer() {
	solution = $("#snippet-field").val().toLowerCase();
	sentence = $("#main-sentence").text().toLowerCase();
	sentence = sentence.replace(/[^\w\s]|_/g, "").replace(/\s+/g, " ");
	tokens = sentence.split(" ");
	for(i in tokens){
		if(stopwords.indexOf(tokens[i]) < 0)
			if(solution.indexOf(tokens[i]) !== -1) 
				return true;
	}
	bolts[13] = 3;
	boltsFreq[13] += 1;
	display_error("The words for which you didn't find a suitable sentence block do not appear in the original sentence to recreate.",$("#snippet-field"),5000);
	return false;
}

function checkTime(){
	var timenow = new Date().getTime();
	var interval  = timenow - starttime;
	if (interval < 10000) {
		bolts[10] = 10;
		boltsFreq[10] += 1;
		display_error("Warning: you attempted to submit your answer too soon! Are you sure you have already completed the task?",$("#sortable"),10000);
	} else return true;
}

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
		bolts[8] = 1;
		boltsFreq[8] += 1;
		$('#value-field-id').prop('title', ' ');
		//$("#value-field-id").uitooltip( "option", "disabled", false );
		$("#value-field-id").uitooltip({
			  content: "This field cannot be empty."
		});
		$("#value-field-id").uitooltip( "open" );
		return false;
	}
	if(type == "http://www.w3.org/2001/XMLSchema#decimal" && isNaN(val)) {
		bolts[7] = 1;
		boltsFreq[7] += 1;
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
	if($("#searchfield").val().trim().length > 0) for(index in jsonData){
		score = scoreSimilarity(keywords, jsonData[index])
		if(score > 0){
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

function scoreWordSimilarity(word1, word2) {
	if(word1 == word2) return 4;
	if(word1.indexOf(word2) != -1 || word2.indexOf(word1) != -1) return 1;
	return longestCommonSubstring(word1, word2)/Math.min(word1.length, word2.length);
}

function scoreSimilarity(keywords, predicate){
	var scores = {};
	var highestScore = 0
	for(var i = 0; i < keywords.length; i++){
		scores[i] = 0;
	}
	var token_num = 0;
	for(labelI in predicate["label"]){
		if(isNaN(predicate["label"][labelI])){
			tokens = [];
			if(typeof predicate["label"][labelI] =='object'){
				if(predicate["label"][labelI]["isURI"] == false)
					tokens = predicate["label"][labelI]["value"].split(" ");
			}
			else tokens = predicate["label"][labelI].split(" ");
			for(token in tokens){
				if(stopwords.indexOf(tokens[token].toLowerCase().trim()) < 0) token_num += 1;
				for(wordI in keywords){
					if(stopwords.indexOf(keywords[wordI].toLowerCase().trim()) < 0 && stopwords.indexOf(tokens[token].toLowerCase().trim()) < 0 ){						
						scores[wordI] = Math.max(scores[wordI], scoreWordSimilarity(keywords[wordI].toLowerCase().trim(), tokens[token].toLowerCase().trim()));
						highestScore = Math.max(highestScore,scores[wordI]);
					}
						
					/*if(stopwords.indexOf(keywords[wordI].toLowerCase().trim()) < 0 && stopwords.indexOf(tokens[token].toLowerCase().trim()) < 0 ){						
						if(keywords[wordI].toLowerCase().trim() == tokens[token].toLowerCase().trim()) 
							score++;
						else {
							score_cumulative = score_cumulative + Math.pow(1/levenshteinDistance(keywords[wordI].toLowerCase().trim(), tokens[token].toLowerCase().trim()),3);
							cumulative_num++;
						}
					}*/
				} 
			}
			
		}
	}
	var keyword_length = 0;
	for(wordI in keywords){
		if(stopwords.indexOf(keywords[wordI].toLowerCase().trim()) < 0) keyword_length++;
	}
	var total_score = Object.values(scores).reduce(add, 0);
	var token_difference = Math.max(token_num-keyword_length, keyword_length-token_num);
	if(highestScore >= 1) return 10+total_score-token_difference;
	else return 0;
}
function add(a, b) {
    return a + b;
}
function longestCommonSubstring(string1, string2){
	// init max value
	var longestCommonSubstring = 0;
	// init 2D array with 0
	var table = [],
            len1 = string1.length,
            len2 = string2.length,
            row, col;
	for(row = 0; row <= len1; row++){
		table[row] = [];
		for(col = 0; col <= len2; col++){
			table[row][col] = 0;
		}
	}
	// fill table
        var i, j;
	for(i = 0; i < len1; i++){
		for(j = 0; j < len2; j++){
			if(string1[i] === string2[j]){
				if(table[i][j] === 0){
					table[i+1][j+1] = 1;
				} else {
					table[i+1][j+1] = table[i][j] + 1;
				}
				if(table[i+1][j+1] > longestCommonSubstring){
					longestCommonSubstring = table[i+1][j+1];
				}
			} else {
				table[i+1][j+1] = 0;
			}
		}
	}
	return longestCommonSubstring;
}

function addPredicate(predicate, score){
	var content = "";
	// create the label
	for (labelI in predicate["label"]){
		var label = predicate["label"][labelI];
		if(isNaN(label)){
			content += label+" ";
		} else {
			content += '<span class="block-variable" var-num="'+label+'">'+getVariableSelection()+"</span> ";
		}
	};
	// create the pre-existing fields
	for (varI in predicate["propertyVariables"]){
		var varObject = predicate["propertyVariables"][varI];
		if(isNaN(varObject)){
			var URI = $(varObject).attr('URI');
			if (typeof URI !== typeof undefined && URI !== false) {
			    // it is a URI
				content += '<span class="pre-initialised-variable" var-type="URI" var-num="'+varI+'"'
					+ ' value-uri="' + URI + '"'
					+ '></span> ';
			} else {
				// it is a literal
				content += '<span class="pre-initialised-variable" var-type="literal" var-num="'+varI+'"'
					+ ' value-literal="' + $(varObject).attr('lexicalValue') + '"'
					+ ' value-literal-type="' + $(varObject).attr('datatype') + '"'
					+ '></span> ';
			}
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
	'<option value="X" class="varoption varoptionc7">X</option>'+
	'<option value="Y" class="varoption varoptionc5">Y</option>'+
	'<option value="Z" class="varoption varoptionc6">Z</option>'+
	'<option value="W" class="varoption varoptionc4">W</option>'+
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
function getNoJsonResult(){
	if($("#snippet-field").val().length > 0){		
		data = {
				nosolution: $("#snippet-field").val()
		}
		return data;
	} else return null;
}

function getJsonResult(){
	emptyErrorMessage = "Warning: the solution is empty. Please drag and drop here the blocks that you think make the best solution.";
	var remainingMandatoryVars = [];
	remainingMandatoryVars = remainingMandatoryVars.concat(mandatoryVars);
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
							bolts[6] = 2;
							boltsFreq[6] += 1;
							throw "Warning: not all fields have been chosen in your solution. Please choose a variable, or a number/text if required.";
						}
						object.variables.push({
							varnum: varnumber,
							type: "variable",
							val: value
						});
						var indexToRemove = -1
						for(mvIndex in remainingMandatoryVars){							
							if(value == remainingMandatoryVars[mvIndex]) indexToRemove = mvIndex;
						}
						if(indexToRemove != -1){
							remainingMandatoryVars.splice(indexToRemove,1);
						}
						
					} else {
						// it is a constant field
						value = variable.find(".entity-span-val").text();
						lit_type = variable.find(".entity-span-type").text();
						if(value.trim().length <= 0) {
							bolts[5] = 2;		
							boltsFreq[5] += 1;
							throw "ERROR, not all entities have been initialised in your solution.";
						}
						object.variables.push({
							varnum: varnumber,
							type: lit_type,
							val: value
						});
					}
				});
				// get the pre-existing variable bindings
				item.find(".pre-initialised-variable").each(function( index ) {
					variable = $(this);
					vartype = variable.attr("var-type");
					varnumber = variable.attr("var-num");
					if(vartype == "URI"){
						object.variables.push({
							varnum: varnumber,
							type: "URI",
							val: variable.attr("value-uri")
						});	
					} else if(vartype == "literal"){
						object.variables.push({
							varnum: varnumber,
							type: variable.attr("value-literal-type"),
							val: variable.attr("value-literal")
						});	
					}
				});
				
				
				if(object.variables.length < blockvarnum) {
					bolts[4] = 2;
					boltsFreq[4] += 1;
					throw "Error, a predicate does not have all the required variables.";
				}
				if(scope == "if"){					
					if(data.then_block.length > 0) {
						bolts[3] = 2;
						boltsFreq[3] += 1;
						throw "Warning: you cannot put 'if' statements after 'then'. Please move your if statements before the 'then' block.";
					}
					data.if_block.push(object);
				} else if(scope == "then"){
					if(data.if_block.length < 1) {
						bolts[2] = 4;
						boltsFreq[2] += 1;
						throw "Warning: you have statements after 'then' but there are no 'if' statement before 'then'. To fix this, add the 'if' block and its conditions before 'then'.";
					}
					data.then_block.push(object);
				} else {
					data.unassigned.push(object);
				}
			}
		});
		if(isIF && data.if_block.length == 0){
			bolts[11] = 3;
			boltsFreq[11] += 1;
			throw 'Warning: the sentence you have to recreate contains an "if", but your solution does not contain any "if" statements';
		}
		if(mandatoryVars != null && remainingMandatoryVars.length > 0){
			bolts[12] = 3;
			boltsFreq[12] += 1;
			throw 'Warning: the sentence you have to recreate contains variable "'+remainingMandatoryVars[0]+'", but you have not used this variable in your solution.';
		}
		if(data.if_block.length > 0 &&  data.then_block.length <= 0 && data.unassigned.length <= 0) {			
			bolts[1] = 4;
			boltsFreq[1] += 1;
			throw "Warning: you have statements after 'if', but there is no statement after 'then'.";
		}
		if(data.if_block.length <= 0 &&  data.then_block.length <= 0 && data.unassigned.length <= 0) 
			throw emptyErrorMessage;
		return data;
	} catch(err) {
		if(err == emptyErrorMessage) {
			bolts[0] = 10;
			boltsFreq[0] += 1;
		}
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