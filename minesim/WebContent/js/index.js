$( function() {
	
		
		var maxWidth  = $('#outer').width()+50;
		var maxHeight = $('#outer').height()+50;

		$(window).resize(function(evt) {
		    var $window = $(window);
		    var width = $window.width();
		    var height = $window.height();
		    var scale;
		    
		    scale = Math.min((width)/maxWidth, (height)/maxHeight);
		    
		    $('#outer').css({'-webkit-transform': 'scale(' + scale + ')'});
		    $('#wrap').css({ width: maxWidth * scale, height: maxHeight * scale });
		});
		$(window).resize();
});