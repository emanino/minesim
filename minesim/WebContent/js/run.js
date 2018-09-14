$( function() {
	
	generateMineCanvas("mineVisualizationArea1",mine1, "mineSvg", "gtablediv", "tableArea", 1, true, "ID1");
	
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
	
	var maxWidth  = $('.outer').width()*1;
	var maxHeight = $('.outer').height()*1.2;

	$(window).resize(function(evt) {
		
		$wHeight = $(window).height();
		$item.height($wHeight);
		  
	    var $window = $(window);
	    var width = $window.width();
	    var height = $window.height();
	    var scale;
	    
	    scale = Math.min((width)/maxWidth, (height)/maxHeight);
	    $('.outer').css({'-webkit-transform': 'scale(' + scale + ')'});
	    $('.wrapper').css({ width: maxWidth * scale, height: maxHeight * scale*0.85 });
	});
	$(window).resize();
	
	$('.carousel').on('slid.bs.carousel', '', function() {
		  var $this = $(this);
		  $this.children('.carousel-control').show();
		  if($('.carousel-inner .item:first').hasClass('active')) {
		    $this.children('.left.carousel-control').hide();
		  } else if($('.carousel-inner .item:last').hasClass('active')) {
		    $this.children('.right.carousel-control').hide();
		  }
	});
	
	
	
	
	
	
});

var mine1 = {"mineObjects" : [
	  {
		    "type": "maintunnel",
		    "name": "T0",
		    "c1": [
		      0,
		      0,
		      0
		    ],
		    "c2": [
		      0,
		      144,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A1",
		    "c": [
		      0,
		      0.39999999999999997,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A2",
		    "c": [
		      0,
		      1.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A3",
		    "c": [
		      0,
		      2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A4",
		    "c": [
		      0,
		      2.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A5",
		    "c": [
		      0,
		      3.5999999999999996,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A6",
		    "c": [
		      0,
		      4.3999999999999995,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A7",
		    "c": [
		      0,
		      5.199999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A8",
		    "c": [
		      0,
		      5.999999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A9",
		    "c": [
		      0,
		      6.799999999999999,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T10",
		    "c1": [
		      -40,
		      0,
		      0
		    ],
		    "c2": [
		      -40,
		      144,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A11",
		    "c": [
		      -2,
		      0.39999999999999997,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A12",
		    "c": [
		      -2,
		      1.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A13",
		    "c": [
		      -2,
		      2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A14",
		    "c": [
		      -2,
		      2.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A15",
		    "c": [
		      -2,
		      3.5999999999999996,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A16",
		    "c": [
		      -2,
		      4.3999999999999995,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A17",
		    "c": [
		      -2,
		      5.199999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A18",
		    "c": [
		      -2,
		      5.999999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A19",
		    "c": [
		      -2,
		      6.799999999999999,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T20",
		    "c1": [
		      0,
		      144,
		      0
		    ],
		    "c2": [
		      -40,
		      144,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A21",
		    "c": [
		      -0.3333333333333333,
		      7.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A22",
		    "c": [
		      -1,
		      7.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A23",
		    "c": [
		      -1.6666666666666665,
		      7.2,
		      0
		    ]
		  },
		  {
		    "type": "maintunnel",
		    "name": "T24",
		    "c1": [
		      0,
		      144,
		      0
		    ],
		    "c2": [
		      0,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A25",
		    "c": [
		      0,
		      7.6000000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A26",
		    "c": [
		      0,
		      8.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A27",
		    "c": [
		      0,
		      9.200000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A28",
		    "c": [
		      0,
		      10.000000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A29",
		    "c": [
		      0,
		      10.800000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A30",
		    "c": [
		      0,
		      11.600000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A31",
		    "c": [
		      0,
		      12.400000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A32",
		    "c": [
		      0,
		      13.200000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A33",
		    "c": [
		      0,
		      14.000000000000005,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T34",
		    "c1": [
		      -40,
		      144,
		      0
		    ],
		    "c2": [
		      -40,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A35",
		    "c": [
		      -2,
		      7.6000000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A36",
		    "c": [
		      -2,
		      8.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A37",
		    "c": [
		      -2,
		      9.200000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A38",
		    "c": [
		      -2,
		      10.000000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A39",
		    "c": [
		      -2,
		      10.800000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A40",
		    "c": [
		      -2,
		      11.600000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A41",
		    "c": [
		      -2,
		      12.400000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A42",
		    "c": [
		      -2,
		      13.200000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A43",
		    "c": [
		      -2,
		      14.000000000000005,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T44",
		    "c1": [
		      0,
		      288,
		      0
		    ],
		    "c2": [
		      -40,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A45",
		    "c": [
		      -0.3333333333333333,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A46",
		    "c": [
		      -1,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A47",
		    "c": [
		      -1.6666666666666665,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T48",
		    "c1": [
		      0,
		      288,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A49",
		    "c": [
		      0.4,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A50",
		    "c": [
		      1.2000000000000002,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A51",
		    "c": [
		      2,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A52",
		    "c": [
		      2.8,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A53",
		    "c": [
		      3.5999999999999996,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A54",
		    "c": [
		      4.3999999999999995,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A55",
		    "c": [
		      5.199999999999999,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S56",
		    "c": [
		      112.00000000000001,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "maintunnel",
		    "name": "T57",
		    "c1": [
		      0,
		      288,
		      0
		    ],
		    "c2": [
		      0,
		      432,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A58",
		    "c": [
		      0,
		      14.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A59",
		    "c": [
		      0,
		      15.600000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A60",
		    "c": [
		      0,
		      16.400000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A61",
		    "c": [
		      0,
		      17.200000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A62",
		    "c": [
		      0,
		      18.000000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A63",
		    "c": [
		      0,
		      18.800000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A64",
		    "c": [
		      0,
		      19.600000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A65",
		    "c": [
		      0,
		      20.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A66",
		    "c": [
		      0,
		      21.200000000000006,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T67",
		    "c1": [
		      -40,
		      288,
		      0
		    ],
		    "c2": [
		      -40,
		      432,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A68",
		    "c": [
		      -2,
		      14.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A69",
		    "c": [
		      -2,
		      15.600000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A70",
		    "c": [
		      -2,
		      16.400000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A71",
		    "c": [
		      -2,
		      17.200000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A72",
		    "c": [
		      -2,
		      18.000000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A73",
		    "c": [
		      -2,
		      18.800000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A74",
		    "c": [
		      -2,
		      19.600000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A75",
		    "c": [
		      -2,
		      20.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A76",
		    "c": [
		      -2,
		      21.200000000000006,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T77",
		    "c1": [
		      0,
		      432,
		      0
		    ],
		    "c2": [
		      -40,
		      432,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A78",
		    "c": [
		      -0.3333333333333333,
		      21.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A79",
		    "c": [
		      -1,
		      21.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A80",
		    "c": [
		      -1.6666666666666665,
		      21.6,
		      0
		    ]
		  },
		  {
		    "type": "maintunnel",
		    "name": "T81",
		    "c1": [
		      0,
		      432,
		      0
		    ],
		    "c2": [
		      0,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A82",
		    "c": [
		      0,
		      22,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A83",
		    "c": [
		      0,
		      22.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A84",
		    "c": [
		      0,
		      23.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A85",
		    "c": [
		      0,
		      24.400000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A86",
		    "c": [
		      0,
		      25.200000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A87",
		    "c": [
		      0,
		      26.000000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A88",
		    "c": [
		      0,
		      26.800000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A89",
		    "c": [
		      0,
		      27.600000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A90",
		    "c": [
		      0,
		      28.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T91",
		    "c1": [
		      -40,
		      432,
		      0
		    ],
		    "c2": [
		      -40,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A92",
		    "c": [
		      -2,
		      22,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A93",
		    "c": [
		      -2,
		      22.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A94",
		    "c": [
		      -2,
		      23.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A95",
		    "c": [
		      -2,
		      24.400000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A96",
		    "c": [
		      -2,
		      25.200000000000003,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A97",
		    "c": [
		      -2,
		      26.000000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A98",
		    "c": [
		      -2,
		      26.800000000000004,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A99",
		    "c": [
		      -2,
		      27.600000000000005,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A100",
		    "c": [
		      -2,
		      28.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "escapetunnel",
		    "name": "T101",
		    "c1": [
		      0,
		      576,
		      0
		    ],
		    "c2": [
		      -40,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A102",
		    "c": [
		      -0.3333333333333333,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A103",
		    "c": [
		      -1,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A104",
		    "c": [
		      -1.6666666666666665,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T105",
		    "c1": [
		      0,
		      576,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A106",
		    "c": [
		      0.4,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A107",
		    "c": [
		      1.2000000000000002,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A108",
		    "c": [
		      2,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A109",
		    "c": [
		      2.8,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A110",
		    "c": [
		      3.5999999999999996,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A111",
		    "c": [
		      4.3999999999999995,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A112",
		    "c": [
		      5.199999999999999,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T113",
		    "c1": [
		      112.00000000000001,
		      576,
		      0
		    ],
		    "c2": [
		      224.00000000000003,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A114",
		    "c": [
		      6.000000000000001,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A115",
		    "c": [
		      6.800000000000001,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A116",
		    "c": [
		      7.6000000000000005,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A117",
		    "c": [
		      8.4,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A118",
		    "c": [
		      9.200000000000001,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A119",
		    "c": [
		      10.000000000000002,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A120",
		    "c": [
		      10.800000000000002,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T121",
		    "c1": [
		      224.00000000000003,
		      576,
		      0
		    ],
		    "c2": [
		      336,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A122",
		    "c": [
		      11.600000000000001,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A123",
		    "c": [
		      12.400000000000002,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A124",
		    "c": [
		      13.200000000000003,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A125",
		    "c": [
		      14.000000000000004,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A126",
		    "c": [
		      14.800000000000004,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A127",
		    "c": [
		      15.600000000000005,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A128",
		    "c": [
		      16.400000000000006,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S129",
		    "c": [
		      336,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T130",
		    "c1": [
		      224.00000000000003,
		      576,
		      0
		    ],
		    "c2": [
		      224.00000000000003,
		      512,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A131",
		    "c": [
		      11.200000000000001,
		      28.400000000000002,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A132",
		    "c": [
		      11.200000000000001,
		      27.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A133",
		    "c": [
		      11.200000000000001,
		      26.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A134",
		    "c": [
		      11.200000000000001,
		      26,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S135",
		    "c": [
		      224.00000000000003,
		      512,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T136",
		    "c1": [
		      112.00000000000001,
		      576,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      640,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A137",
		    "c": [
		      5.6000000000000005,
		      29.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A138",
		    "c": [
		      5.6000000000000005,
		      30,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A139",
		    "c": [
		      5.6000000000000005,
		      30.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A140",
		    "c": [
		      5.6000000000000005,
		      31.6,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S141",
		    "c": [
		      112.00000000000001,
		      640,
		      0
		    ]
		  },
		  {
		    "type": "maintunnel",
		    "name": "T142",
		    "c1": [
		      0,
		      576,
		      0
		    ],
		    "c2": [
		      0,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A143",
		    "c": [
		      0,
		      29.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A144",
		    "c": [
		      0,
		      30,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A145",
		    "c": [
		      0,
		      30.8,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A146",
		    "c": [
		      0,
		      31.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A147",
		    "c": [
		      0,
		      32.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A148",
		    "c": [
		      0,
		      33.199999999999996,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A149",
		    "c": [
		      0,
		      33.99999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A150",
		    "c": [
		      0,
		      34.79999999999999,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A151",
		    "c": [
		      0,
		      35.59999999999999,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T152",
		    "c1": [
		      0,
		      720,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A153",
		    "c": [
		      0.4,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A154",
		    "c": [
		      1.2000000000000002,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A155",
		    "c": [
		      2,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A156",
		    "c": [
		      2.8,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A157",
		    "c": [
		      3.5999999999999996,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A158",
		    "c": [
		      4.3999999999999995,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A159",
		    "c": [
		      5.199999999999999,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T160",
		    "c1": [
		      112.00000000000001,
		      720,
		      0
		    ],
		    "c2": [
		      224.00000000000003,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A161",
		    "c": [
		      6.000000000000001,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A162",
		    "c": [
		      6.800000000000001,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A163",
		    "c": [
		      7.6000000000000005,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A164",
		    "c": [
		      8.4,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A165",
		    "c": [
		      9.200000000000001,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A166",
		    "c": [
		      10.000000000000002,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A167",
		    "c": [
		      10.800000000000002,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T168",
		    "c1": [
		      224.00000000000003,
		      720,
		      0
		    ],
		    "c2": [
		      336,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A169",
		    "c": [
		      11.600000000000001,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A170",
		    "c": [
		      12.400000000000002,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A171",
		    "c": [
		      13.200000000000003,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A172",
		    "c": [
		      14.000000000000004,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A173",
		    "c": [
		      14.800000000000004,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A174",
		    "c": [
		      15.600000000000005,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A175",
		    "c": [
		      16.400000000000006,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S176",
		    "c": [
		      336,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T177",
		    "c1": [
		      112.00000000000001,
		      720,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      784,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A178",
		    "c": [
		      5.6000000000000005,
		      36.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A179",
		    "c": [
		      5.6000000000000005,
		      37.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A180",
		    "c": [
		      5.6000000000000005,
		      38,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A181",
		    "c": [
		      5.6000000000000005,
		      38.8,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T182",
		    "c1": [
		      112.00000000000001,
		      784,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      848.0000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A183",
		    "c": [
		      5.6000000000000005,
		      39.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A184",
		    "c": [
		      5.6000000000000005,
		      40.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A185",
		    "c": [
		      5.6000000000000005,
		      41.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A186",
		    "c": [
		      5.6000000000000005,
		      42,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S187",
		    "c": [
		      112.00000000000001,
		      848.0000000000001,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T188",
		    "c1": [
		      112.00000000000001,
		      720,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      784,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A189",
		    "c": [
		      5.6000000000000005,
		      36.4,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A190",
		    "c": [
		      5.6000000000000005,
		      37.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A191",
		    "c": [
		      5.6000000000000005,
		      38,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A192",
		    "c": [
		      5.6000000000000005,
		      38.8,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T193",
		    "c1": [
		      112.00000000000001,
		      784,
		      0
		    ],
		    "c2": [
		      112.00000000000001,
		      848.0000000000001,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A194",
		    "c": [
		      5.6000000000000005,
		      39.6,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A195",
		    "c": [
		      5.6000000000000005,
		      40.400000000000006,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A196",
		    "c": [
		      5.6000000000000005,
		      41.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A197",
		    "c": [
		      5.6000000000000005,
		      42,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S198",
		    "c": [
		      112.00000000000001,
		      848.0000000000001,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T199",
		    "c1": [
		      112.00000000000001,
		      784,
		      0
		    ],
		    "c2": [
		      160,
		      784,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A200",
		    "c": [
		      6,
		      39.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A201",
		    "c": [
		      6.8,
		      39.2,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A202",
		    "c": [
		      7.6,
		      39.2,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S203",
		    "c": [
		      160,
		      784,
		      0
		    ]
		  },
		  {
		    "type": "tunnel",
		    "name": "T204",
		    "c1": [
		      0,
		      720,
		      0
		    ],
		    "c2": [
		      -112.00000000000001,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A205",
		    "c": [
		      -0.4,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A206",
		    "c": [
		      -1.2000000000000002,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A207",
		    "c": [
		      -2,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A208",
		    "c": [
		      -2.8,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A209",
		    "c": [
		      -3.5999999999999996,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A210",
		    "c": [
		      -4.3999999999999995,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "atom",
		    "name": "A211",
		    "c": [
		      -5.199999999999999,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "site",
		    "name": "S212",
		    "c": [
		      -112.00000000000001,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "minerperson",
		    "name": "P213",
		    "c": [
		      103.99999999999999,
		      288,
		      0
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "214",
		    "propertyName": "Location",
		    "c": [
		      103.99999999999999,
		      288,
		      0
		    ],
		    "reading": [
		      5.199999999999999,
		      14.4,
		      0
		    ]
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Location",
		    "data": [
		      {
		        "value": "214",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": [
		          5.199999999999999,
		          14.4,
		          0
		        ],
		        "type": "reading",
		        "label": "Reading (coordinates)"
		      }
		    ]
		  },
		  {
		    "type": "minerperson",
		    "name": "P215",
		    "c": [
		      227.0550680975706,
		      576,
		      0
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "216",
		    "propertyName": "Location",
		    "c": [
		      227.0550680975706,
		      576,
		      0
		    ],
		    "reading": [
		      8.85275340487853,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Location",
		    "data": [
		      {
		        "value": "216",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": [
		          8.85275340487853,
		          28.8,
		          0
		        ],
		        "type": "reading",
		        "label": "Reading (coordinates)"
		      }
		    ]
		  },
		  {
		    "type": "minerperson",
		    "name": "P217",
		    "c": [
		      220.6747564224193,
		      571.3252435775807,
		      0
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "218",
		    "propertyName": "Location",
		    "c": [
		      220.6747564224193,
		      571.3252435775807,
		      0
		    ],
		    "reading": [
		      8.630555196668805,
		      28.8,
		      0
		    ]
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Location",
		    "data": [
		      {
		        "value": "218",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": [
		          8.630555196668805,
		          28.8,
		          0
		        ],
		        "type": "reading",
		        "label": "Reading (coordinates)"
		      }
		    ]
		  },
		  {
		    "type": "minerperson",
		    "name": "P219",
		    "c": [
		      112.00000000000001,
		      632,
		      0
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "220",
		    "propertyName": "Location",
		    "c": [
		      112.00000000000001,
		      632,
		      0
		    ],
		    "reading": [
		      5.6000000000000005,
		      31.6,
		      0
		    ]
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Location",
		    "data": [
		      {
		        "value": "220",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": [
		          5.6000000000000005,
		          31.6,
		          0
		        ],
		        "type": "reading",
		        "label": "Reading (coordinates)"
		      }
		    ]
		  },
		  {
		    "type": "minerperson",
		    "name": "P221",
		    "c": [
		      80.28302735419953,
		      720,
		      0
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "222",
		    "propertyName": "Location",
		    "c": [
		      80.28302735419953,
		      720,
		      0
		    ],
		    "reading": [
		      1.5141513677099763,
		      36,
		      0
		    ]
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Location",
		    "data": [
		      {
		        "value": "222",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": [
		          1.5141513677099763,
		          36,
		          0
		        ],
		        "type": "reading",
		        "label": "Reading (coordinates)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "223",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      24,
		      0
		    ],
		    "reading": "24.202541695115695"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "223",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.202541695115695",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "224",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      24,
		      0
		    ],
		    "reading": "0.19039092848560202"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "224",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.19039092848560202",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "225",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      72,
		      0
		    ],
		    "reading": "23.67872058541849"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "225",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.67872058541849",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "226",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      72,
		      0
		    ],
		    "reading": "0.6559263280326093"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "226",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.6559263280326093",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "227",
		    "propertyName": "Temperature",
		    "c": [
		      -6.666666666666666,
		      144,
		      0
		    ],
		    "reading": "24.227190641970758"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "227",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.227190641970758",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "228",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -6.666666666666666,
		      144,
		      0
		    ],
		    "reading": "0.09005480892510423"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "228",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.09005480892510423",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "229",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      135.99999999999997,
		      0
		    ],
		    "reading": "24.13138816441508"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "229",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.13138816441508",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "230",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      135.99999999999997,
		      0
		    ],
		    "reading": "0.2284283355181377"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "230",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.2284283355181377",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "231",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      87.99999999999999,
		      0
		    ],
		    "reading": "23.887479558827817"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "231",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.887479558827817",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "232",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      87.99999999999999,
		      0
		    ],
		    "reading": "0.26526455694620515"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "232",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.26526455694620515",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "233",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      184.00000000000003,
		      0
		    ],
		    "reading": "24.012454471550694"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "233",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.012454471550694",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "234",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      184.00000000000003,
		      0
		    ],
		    "reading": "0.16065057648485842"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "234",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.16065057648485842",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "235",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      216.00000000000006,
		      0
		    ],
		    "reading": "23.93387234044294"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "235",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.93387234044294",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "236",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      216.00000000000006,
		      0
		    ],
		    "reading": "0.2538539576656374"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "236",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.2538539576656374",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "237",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      264.0000000000001,
		      0
		    ],
		    "reading": "24.040347466506233"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "237",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.040347466506233",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "238",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      264.0000000000001,
		      0
		    ],
		    "reading": "0.14290557327983128"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "238",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.14290557327983128",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "239",
		    "propertyName": "Temperature",
		    "c": [
		      -6.666666666666666,
		      288,
		      0
		    ],
		    "reading": "24.015772442050917"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "239",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.015772442050917",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "240",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -6.666666666666666,
		      288,
		      0
		    ],
		    "reading": "0.18397118155737158"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "240",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.18397118155737158",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "241",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      216.00000000000006,
		      0
		    ],
		    "reading": "23.8069613862051"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "241",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.8069613862051",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "242",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      216.00000000000006,
		      0
		    ],
		    "reading": "0.6047980679358658"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "242",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.6047980679358658",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "243",
		    "propertyName": "Temperature",
		    "c": [
		      8,
		      288,
		      0
		    ],
		    "reading": "23.872403975261577"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "243",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.872403975261577",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "244",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      8,
		      288,
		      0
		    ],
		    "reading": "0.22792795814212394"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "244",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.22792795814212394",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "245",
		    "propertyName": "Temperature",
		    "c": [
		      72,
		      288,
		      0
		    ],
		    "reading": "23.616878673543425"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "245",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.616878673543425",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "246",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      72,
		      288,
		      0
		    ],
		    "reading": "0.5727545252150519"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "246",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.5727545252150519",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "247",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      328.00000000000006,
		      0
		    ],
		    "reading": "24.046557657341353"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "247",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.046557657341353",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "248",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      328.00000000000006,
		      0
		    ],
		    "reading": "0.24623349396632926"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "248",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.24623349396632926",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "249",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      424.0000000000001,
		      0
		    ],
		    "reading": "24.192379968538127"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "249",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.192379968538127",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "250",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      424.0000000000001,
		      0
		    ],
		    "reading": "0.34331941542805905"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "250",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.34331941542805905",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "251",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      408.0000000000001,
		      0
		    ],
		    "reading": "24.022828545931652"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "251",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.022828545931652",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "252",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      408.0000000000001,
		      0
		    ],
		    "reading": "0.2350236229838225"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "252",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.2350236229838225",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "253",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      360.00000000000006,
		      0
		    ],
		    "reading": "23.555330066164895"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "253",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.555330066164895",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "254",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      360.00000000000006,
		      0
		    ],
		    "reading": "0.10473948597789327"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "254",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.10473948597789327",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "255",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      296,
		      0
		    ],
		    "reading": "24.067055665222988"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "255",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.067055665222988",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "256",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      296,
		      0
		    ],
		    "reading": "0.20199079510758153"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "256",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.20199079510758153",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "257",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      440,
		      0
		    ],
		    "reading": "23.814532577965785"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "257",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.814532577965785",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "258",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      440,
		      0
		    ],
		    "reading": "0.09002283323532907"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "258",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.09002283323532907",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "259",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      504.00000000000006,
		      0
		    ],
		    "reading": "24.167270276615742"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "259",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.167270276615742",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "260",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      504.00000000000006,
		      0
		    ],
		    "reading": "0.2993855593366963"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "260",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.2993855593366963",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "261",
		    "propertyName": "Temperature",
		    "c": [
		      -40,
		      568.0000000000001,
		      0
		    ],
		    "reading": "24.66535688867045"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "261",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.66535688867045",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "262",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -40,
		      568.0000000000001,
		      0
		    ],
		    "reading": "0.28357483738447303"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "262",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.28357483738447303",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "263",
		    "propertyName": "Temperature",
		    "c": [
		      -6.666666666666666,
		      576,
		      0
		    ],
		    "reading": "24.2107374605975"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "263",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.2107374605975",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "264",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -6.666666666666666,
		      576,
		      0
		    ],
		    "reading": "0.12406092346179363"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "264",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.12406092346179363",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "265",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      536.0000000000001,
		      0
		    ],
		    "reading": "24.239313549475945"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "265",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.239313549475945",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "266",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      536.0000000000001,
		      0
		    ],
		    "reading": "0.27089313127300424"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "266",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.27089313127300424",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "267",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      456,
		      0
		    ],
		    "reading": "23.810396947784007"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "267",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.810396947784007",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "268",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      456,
		      0
		    ],
		    "reading": "0.13395270313986624"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "268",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.13395270313986624",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "269",
		    "propertyName": "Temperature",
		    "c": [
		      40,
		      576,
		      0
		    ],
		    "reading": "24.123885841818527"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "269",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.123885841818527",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "270",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      40,
		      576,
		      0
		    ],
		    "reading": "0.18750530073908686"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "270",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.18750530073908686",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "271",
		    "propertyName": "Temperature",
		    "c": [
		      72,
		      576,
		      0
		    ],
		    "reading": "24.268666793890798"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "271",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.268666793890798",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "272",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      72,
		      576,
		      0
		    ],
		    "reading": "0.554313563665144"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "272",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.554313563665144",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "273",
		    "propertyName": "Temperature",
		    "c": [
		      136,
		      576,
		      0
		    ],
		    "reading": "23.72032930095007"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "273",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.72032930095007",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "274",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      136,
		      576,
		      0
		    ],
		    "reading": "0.289917760007863"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "274",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.289917760007863",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "275",
		    "propertyName": "Temperature",
		    "c": [
		      248.00000000000006,
		      576,
		      0
		    ],
		    "reading": "24.160400399250015"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "275",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.160400399250015",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "276",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      248.00000000000006,
		      576,
		      0
		    ],
		    "reading": "0.06364967411859324"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "276",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.06364967411859324",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "277",
		    "propertyName": "Temperature",
		    "c": [
		      280.00000000000006,
		      576,
		      0
		    ],
		    "reading": "24.274653403624495"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "277",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.274653403624495",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "278",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      280.00000000000006,
		      576,
		      0
		    ],
		    "reading": "0.09831753821356833"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "278",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.09831753821356833",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "279",
		    "propertyName": "Temperature",
		    "c": [
		      224.00000000000003,
		      568,
		      0
		    ],
		    "reading": "24.087224294963114"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "279",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.087224294963114",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "280",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      224.00000000000003,
		      568,
		      0
		    ],
		    "reading": "0.4958816763974047"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "280",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.4958816763974047",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "281",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      584,
		      0
		    ],
		    "reading": "23.985115873346217"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "281",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.985115873346217",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "282",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      584,
		      0
		    ],
		    "reading": "0.07528273266109804"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "282",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.07528273266109804",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "283",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      616,
		      0
		    ],
		    "reading": "24.33188471125317"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "283",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.33188471125317",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "284",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      616,
		      0
		    ],
		    "reading": "0.17375779894013899"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "284",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.17375779894013899",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "285",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      616,
		      0
		    ],
		    "reading": "23.877332100502098"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "285",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.877332100502098",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "286",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      616,
		      0
		    ],
		    "reading": "0.3918241728137721"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "286",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.3918241728137721",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "287",
		    "propertyName": "Temperature",
		    "c": [
		      0,
		      695.9999999999998,
		      0
		    ],
		    "reading": "24.04740684319693"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "287",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.04740684319693",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "288",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      0,
		      695.9999999999998,
		      0
		    ],
		    "reading": "0.19720728958706496"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "288",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.19720728958706496",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "289",
		    "propertyName": "Temperature",
		    "c": [
		      24.000000000000004,
		      720,
		      0
		    ],
		    "reading": "23.80693792805373"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "289",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.80693792805373",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "290",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      24.000000000000004,
		      720,
		      0
		    ],
		    "reading": "0.15627163564270977"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "290",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.15627163564270977",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "291",
		    "propertyName": "Temperature",
		    "c": [
		      103.99999999999999,
		      720,
		      0
		    ],
		    "reading": "24.100276456828116"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "291",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.100276456828116",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "292",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      103.99999999999999,
		      720,
		      0
		    ],
		    "reading": "0.28197472058800094"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "292",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.28197472058800094",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "293",
		    "propertyName": "Temperature",
		    "c": [
		      168,
		      720,
		      0
		    ],
		    "reading": "24.007352689147076"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "293",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.007352689147076",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "294",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      168,
		      720,
		      0
		    ],
		    "reading": "0.06857594151898874"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "294",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.06857594151898874",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "295",
		    "propertyName": "Temperature",
		    "c": [
		      216.00000000000006,
		      720,
		      0
		    ],
		    "reading": "24.44157420329417"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "295",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.44157420329417",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "296",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      216.00000000000006,
		      720,
		      0
		    ],
		    "reading": "0.44813827904054915"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "296",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.44813827904054915",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "297",
		    "propertyName": "Temperature",
		    "c": [
		      248.00000000000006,
		      720,
		      0
		    ],
		    "reading": "24.42407257368776"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "297",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.42407257368776",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "298",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      248.00000000000006,
		      720,
		      0
		    ],
		    "reading": "0.1789337624673064"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "298",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.1789337624673064",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "299",
		    "propertyName": "Temperature",
		    "c": [
		      312.0000000000001,
		      720,
		      0
		    ],
		    "reading": "24.254077915874824"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "299",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.254077915874824",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "300",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      312.0000000000001,
		      720,
		      0
		    ],
		    "reading": "0.18500171930800757"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "300",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.18500171930800757",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "301",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      744,
		      0
		    ],
		    "reading": "23.921132567388135"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "301",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.921132567388135",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "302",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      744,
		      0
		    ],
		    "reading": "0.16127806116330473"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "302",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.16127806116330473",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "303",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      840,
		      0
		    ],
		    "reading": "24.218655697739646"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "303",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.218655697739646",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "304",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      840,
		      0
		    ],
		    "reading": "0.10010047197173555"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "304",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.10010047197173555",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "305",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      808.0000000000001,
		      0
		    ],
		    "reading": "24.04608952477176"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "305",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.04608952477176",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "306",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      808.0000000000001,
		      0
		    ],
		    "reading": "0.2989470365792218"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "306",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.2989470365792218",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "307",
		    "propertyName": "Temperature",
		    "c": [
		      112.00000000000001,
		      744,
		      0
		    ],
		    "reading": "23.921132567388135"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "307",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "23.921132567388135",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "308",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      112.00000000000001,
		      744,
		      0
		    ],
		    "reading": "0.16127806116330473"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "308",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.16127806116330473",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "309",
		    "propertyName": "Temperature",
		    "c": [
		      -8,
		      720,
		      0
		    ],
		    "reading": "24.250857250067963"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Temperature",
		    "data": [
		      {
		        "value": "309",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "24.250857250067963",
		        "type": "reading",
		        "label": "Reading (degrees celsius)"
		      }
		    ]
		  },
		  {
		    "type": "sensor",
		    "name": "310",
		    "propertyName": "Carbon Monoxide Concentration",
		    "c": [
		      -8,
		      720,
		      0
		    ],
		    "reading": "0.18970501590430727"
		  },
		  {
		    "type": "infoPredicate",
		    "predicateName": "Carbon Monoxide Concentration",
		    "data": [
		      {
		        "value": "310",
		        "type": "sensor",
		        "label": "Sensor ID"
		      },
		      {
		        "value": "0.18970501590430727",
		        "type": "reading",
		        "label": "Reading (ppm)"
		      }
		    ]
		  }
		]}
;