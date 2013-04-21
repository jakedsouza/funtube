/**
 * @author jake
 */
loadPagination();

function loading_show() {
	$('#latest-videos-spinner').addClass("icon-spin");
}
function loading_hide() {
	$('#latest-videos-spinner').removeClass("icon-spin");
}

function loadPagination() {
	getTotalVideosCount();
}
function getTotalVideosCount() {
	$.ajax({
		type : "POST",
		url : "rest/video/getTotalVideosCount",
		success : function(count) {
			totalVideos = count;
			generatePagination(count);
		}
	});
};

function generatePagination(count) {
//	count = count ;
	loadData(1);
	$('#latest-videos-pagination').bootpag({
		total : count/5 +1, // total pages
		page : 1, // default page
		maxVisible : 5, // visible pagination
		leaps : false
	// next/prev leaps through maxVisible
	}).on("page", function(event, num) {
		debugger;
		loadData(num);
	//	$("#latest-videos-content").html("Page " + num); // or some ajax
															// content
															// loading...
	});
}

function loadData(page) {
	loading_show();
	$("#latest-videos-content").empty();
	$("#latest-videos-content").fadeOut("slow");
	page = page - 1;
	//debugger;

	$.ajax({
		type : "POST",
		url : "rest/video/getTopVideos",
		data : {
			"start" : (page * 5),
			"length" : 5
		},
		success : function(msg) {
			// $("#container").ajaxComplete(function(event, request, settings) {
		debugger;
			loading_hide();
			// alert(msg);
			var s = "";
			for ( var i = 0; i < msg.length; i++) {
				s = s + msg[i].videoTitle + "<br/>";
				// Do something
			}
			// for(var i = 0 ; i < msg)
		//	$("#latest-videos-content").html(s);
			$.Mustache.load('./templates/videothumb.html').done(
					function() {
						$('#latest-videos-content').mustache('video-thumb',
								msg);
						Holder.run(); 
						$(".star").each(function(){
							var totalScore = $(this).next(".totalScore").html();
							var totalScoreCount = $(this).next(".totalScore").next(".totalScoreCount").html();
							totalScore = totalScore.replace ( /[^\d.]/g, '' );
							totalScoreCount = totalScoreCount.replace ( /[^\d.]/g, '' );
							var avgScore = totalScore/totalScoreCount;
							debugger;
							$(this).raty({
								starOff : "img/star-off.png",
								 starOn : "img/star-on.png",
								 starHalf: 'img/star-half.png',
								 score : avgScore,
								 click: function(score, evt) {
									 updateScore(score, $(this));
									    //alert('ID: ' + $(this).attr('id') + "\nscore: " + score + "\nevent: " + evt);
									 }
							});
						
						});
					
					
					});
			$("#latest-videos-content").fadeIn("slow");
		}
	});
}





$("#latest-videos-content").on("click",".videoPlay",function(){
	//var url = $(this).parent().parent().children(".s3URL").html();
	var url =  $.trim($(this).parent().parent().children(".s3URL").html());
	var title = $.trim($(this).parent().parent().children(".videoTitle").html());
	playVideo(url);
	displayALertMessage("Playing video",title,"information");
	
//	debugger;
	
});
$("#latest-videos-content").on("click",".videoThumbnail",function(){
	//var url = $(this).parent().parent().children(".s3URL").html();
	var url =  $.trim($(this).parent().children(".s3URL").html());
	var title = $.trim($(this).parent().children(".videoTitle").html());
	playVideo(url);
	displayALertMessage("Playing video",title,"information");
//	debugger;
	
});

$("#latest-videos-content").on("click",".videoDelete",function(){
	
	var delVideoTitle = $.trim($(this).parent().parent().children(".videoTitle").html());
	var url =  $.trim($(this).parent().parent().children(".s3URL").html());
	$(this).parent().parent().parent().hide('slide', {direction: 'right'}, 1000);
	$.ajax({
		type : "POST",
		url : "rest/video/deleteVideoByS3Url",
		data : {
			"s3Url" : url,
			"delVideoTitle" : delVideoTitle
		},
		success : function(title) {			
				displayALertMessage("Deleted video", title, "error");
		}
	});	

//	debugger;
	
});
function updateScore(score , object){
	var title = $.trim(object.parent().children(".videoTitle").html());
	var s3Name =  $.trim(object.parent().children(".s3Name").html());
	$.ajax({
		type : "POST",
		url : "rest/video/updateScoreByS3Name",
		data : {
			"s3Name" : s3Name,
			"title" : title,
			"score" : score,
		},
		success : function(title) {			
			displayALertMessage("Rating updated","information");
		}
	});	

	debugger;	
}

$("#latest-videos-spinner-btn").on("click",function(){
	loadData(1);
}); 
