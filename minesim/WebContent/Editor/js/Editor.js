var jsonData = [];

function jsonLoaded(json){
	jsonData = json;
}


/**
 * 
 */
$( function() {
	$.getJSON("js/predicates.json", function(json) {
		jsonLoaded(json); // this will show the info it in firebug console
	});
	
  refresh();
  
  $( 'body' ).on('click', '.delbutton', function() {
  	$(this).parent().parent().remove();
  });
  $( 'body' ).on('mouseover', '.draggable', function() {
  	$(this).addClass('ui-state-highlight');
  	$(this).removeClass('ui-state-default');
  });
  $( 'body' ).on('mouseup', '.draggable', function() {
	  	if($(this).parent().attr('id') == "sortable"){
	  		$(this).find("select").prop('disabled', false);
	  	}
	  });
  $( 'body' ).on('mouseout', '.draggable', function() {
  	$(this).addClass('ui-state-default');
  	$(this).removeClass('ui-state-highlight');
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
	  });
  $("#search-button").button( {
      icon: "ui-icon-search",
      showLabel: false
    } );
  $("#search-button").click(function() {
  	search();
  });
  
  $('#searchfield').keypress(function (e) {
  	  if (e.which == 13) {
  		search();
  	    return false;
  	  }
  	});
  
});

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
		if(score > 1){
			objs.push({"score": score, "data": index})
		}
	}
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
			content += getVariableSelection()+" ";
		}
	};
	$("#searchlist").append('  <li class="ui-state-default draglist draggable"><p class="alignleft">'+content+'</p><p class="alignright"><span class="ui-icon ui-icon-trash delbutton"></span></p><div style="clear: both;"></div></li>');
}

function getVariableSelection(){
	return '<select name="vars" class="variableselector" disabled="true">'+
	'<option value=" " class="varoption"> </option>'+
	'<option value="A" class="varoption varoptionc1">A</option>'+
	'<option value="B" class="varoption varoptionc2">B</option>'+
	'<option value="C" class="varoption varoptionc3">C</option>'+
	'<option value="X" class="varoption varoptionc4">X</option>'+
	'<option value="Y" class="varoption varoptionc5">Y</option>'+
	'<option value="Z" class="varoption varoptionc6">Z</option>'+
	'<option value="W" class="varoption varoptionc7">W</option>'+
	'<option value="K" class="varoption varoptionc8">K</option>'+
	'</select>';
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