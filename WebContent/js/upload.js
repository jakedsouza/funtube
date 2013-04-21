var reader;
var bar = document.querySelector('.bar');
function abortRead() {
	if (reader != null)
		reader.abort();
}

function updateProgress(percent) {
	$(".bar").css("width", percent + '%');
}

function resetPage() {
	var validator = $("#fileupload").validate();
	validator.resetForm();
	$('#fileupload')[0].reset();
	abortRead();
	$(".bar").html("");
	// Reset progress indicator on new file selection.
	//document.getElementById('progress').className = '';
	updateProgress(0);
}

function errorHandler(evt) {
	switch (evt.target.error.code) {
	case evt.target.error.NOT_FOUND_ERR:
		displayALertMessage("File Read Error", 'File Not Found!', "error");
		break;
	case evt.target.error.NOT_READABLE_ERR:
		displayALertMessage("File Read Error", 'File is not readable', 'error');
		break;
	case evt.target.error.ABORT_ERR:
		break;
	default:
		displayALertMessage("File Read Error",
				'An error occurred reading this file.', "error");
	}

}

function updateProgressValue(evt) {
	//var bar = document.querySelector('.bar');

	// evt is an ProgressEvent.
	if (evt.lengthComputable) {
		var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
		// Increase the progress bar length.
		if (percentLoaded < 100) {
			updateProgress(percentLoaded);

		}
	}
}

$("#uploadBtn").on("click", function() {
	if (!isFormValid()) {
		return;
	}
	// Reset progress indicator on new file selection.
	//	var bar = document.querySelector('.bar');
	//	var progress = document.querySelector('.progress');
	updateProgress(0);
	//					bar.style.width = '0%';
	//					bar.textContent = '0%';
	//bar.className = "bar";
	var input = document.getElementById("videoFile"), formdata = false;

	if (window.FormData) {
		formdata = new FormData();
	}
	var file = input.files[0];
	// Files[0] = 1st file
	if(!file.type.match('video.mp4')){
		 displayALertMessage("Only videos allowed", "Please upload only video formats", "error");
		 return;
	}
	
	if (!!file.type.match(/*.*/)) {
		reader = new FileReader();
		reader.onerror = errorHandler;
		reader.onprogress = updateProgressValue;
		reader.onabort = function(e) {
			displayALertMessage('File read cancelled', "", "error");
		};
		reader.onloadstart = function(e) {
		};
		reader.onload = function(e) {
			// Ensure that the progress bar displays 100% at the
			// end.
			updateProgress(100);
			//							progress.style.width = '100%';
			//							progress.textContent = '100%';
			//							setTimeout(
			////									"document.getElementById('progress').className='';",
			////									2000);
		};
		reader.readAsDataURL(file);
	}

	if (formdata) {
		var title = $("#videoTitle").val();
		var description = $("#videoDescription").val();
		formdata.append("videoFile", file, file.name);
		formdata.append("videoTitle", title);
		formdata.append("videoDescription", description);
		uploadVideoToServlet(formdata);
	}

});

function isFormValid() {
	var validator = $("#fileupload").validate();
	if (!$("#fileupload").valid()) {
		return false;
	} else {
		return true;
	}
}

function uploadVideoToServlet(formdata) {
	$(".bar").html("Uploading to S3 please wait...");

	// alert(formdata);
	$.ajax({
		url : "rest/video/upload",
		type : "POST",
		data : formdata,
		processData : false,
		contentType : false,
		success : function(res) {
			// var progress = document.querySelector('.progress');
			// progress.textContent = '';
			$("#progress").html("");
			// alert(res);
			displayALertMessage("Upload Success",
					"File uploaded to S3 successfully", "success");
			loadData(1);
			resetPage();
		}
	});
}

$("#closeUploadFormBtn").on("click", function() {
	resetPage();
	$("#fileupload").slideToggle("slow", function() {
		$("#show-hide-video-upload-icon").toggleClass("icon-chevron-down");
	});
});

$('#resetBtn').on("click", function() {
	resetPage();
});

$("#show-hide-video-upload-form").on("click", function() {
	$("#fileupload").slideToggle("slow", function() {
		$("#show-hide-video-upload-icon").toggleClass("icon-chevron-down");
	});
});
