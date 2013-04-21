function displayALertMessage(Atitle, Atext, Atype) {
	//noty({text: 'noty - a jquery notification library!'});

	noty({
		layout : 'bottomRight',
		theme : 'defaultTheme',
		type : Atype,
		text : "<strong>" + Atitle + "</strong> <br/>" + Atext,
		dismissQueue : true, // If you want to use queue feature set this
		// true
		template : '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
		animation : {
			open : {
				height : 'toggle'
			},
			close : {
				height : 'toggle'
			},
			easing : 'swing',
			speed : 500
		// opening & closing animation speed
		},
		timeout : 1000, // delay for closing event. Set false for sticky
		// notifications
		force : false, // adds notification to the beginning of queue when set
		// to true
		modal : false,
		closeWith : [ 'click' ], // ['click', 'button', 'hover']
		buttons : false
	// an array of buttons

	});

	// $.pnotify({
	// title: Atitle,
	// text: Atext,
	// type:Atype,
	// styling: "bootstrap",
	// opacity: .8,
	// animate_speed: 'fast'
	// });
}
function goToByScroll(id) {
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}

function playVideo(url) {
	//	debugger;
		var urlMp4 =url.replace("https://s3.amazonaws.com/funtube/","http://d2hy88m0mk1vb0.cloudfront.net/");
		var urlFlash =url.replace("https://s3.amazonaws.com/funtube/","rmtp://s7xbaerp60tsv.cloudfront.net/cfx/st/");
		urlMp4 = urlFlash ;
	//alert(urlNew);
	goToByScroll();

	$("#video-player").fadeIn("slow");
	$("#video-player-container").fadeIn("slow");
	resizeVideoJS();
	//$("#video-player").attr("src", url);
	//document.getElementById("video-player").load();
	// Once the video is ready
	_V_("video-player").ready(function() {
		
		var myPlayer = _V_("video-player"); // Store the video object
		var aspectRatio = 9 / 16; // Make up an aspect ratio
		window.onresize = resizeVideoJS;
//		myPlayer.src({
//			type : "video/mp4",
//			src : url
//		});
		myPlayer.src([ {
			type : "video/mp4",
			src : urlMp4
		}, {
			type : "video/mp4",
			src : urlFlash
		}]);

		myPlayer.play();

	});
}
function resizeVideoJS() {
	// Get the parent element's actual width
	//    	debugger;
	var myPlayer = _V_("video-player");
	var aspectRatio = 9 / 16;
	var width = document.getElementById(myPlayer.id).parentElement.offsetWidth;
	width = width;
	// Set width to fill parent element, Set height
	myPlayer.width(width).height(width * aspectRatio);
}
//function displayALertMessage(Atitle, Atext, Atype) {
//
//	var noty = noty({text: 'noty - a jquery notification library!'});
//
//}

resizeVideoJS();

$(document).ready(function() {
	//$("#upload-from").hide("fast");
	$("#video-player").hide("fast");
	$("#video-player-container").hide("fast");
});