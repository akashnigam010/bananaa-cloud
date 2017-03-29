$(document).ready(function() {
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");

        $('#typeahead-item-name').typeahead({
	    	minLength: 2,
	    	autoSelect: true,
	    	//showHintOnFocus: true,
	        source: function (query, process) {
	        	var accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCYW5hbmFhIEFwcGxpY2F0aW9uIiwiYXVkIjoiQmFuYW5hYSBBcHBsaWNhdGlvbiIsImlhdCI6MTQ5MDc5MzQwNCwiZXhwIjoxNTIyMzI5NDA0LCJpbmZvIjp7InJvbGUiOiJHVUVTVCJ9fQ.I6VkrPf3JA5VQxDHSiPal3sFKPrXk8-c4y7SnXdOHxQ";
	        	var dataOb = {
	        			searchString : query
	        	};
	            return $.ajax({
	          	  method: "POST",
	          	  url: "/socyal/merchant/searchMerchant",
	          	  contentType : "application/json",
	          	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
	          	  data: JSON.stringify(dataOb)
	          	})
	          	  .done(function(response) {
	          		  return process(response.merchants);
	          	  });
	        }
	    });
        
        $('#addRecommendButton').on('mouseup', function (e) {
        	openRecommendationModal('', '', false);
        });

        $(".recommended-item").on('mouseup', function(e){
        	var id = e.currentTarget.childNodes[1].childNodes[1].childNodes[0].data;
        	var hash = e.currentTarget.childNodes[1].childNodes[3].childNodes[0].data;
        	var name = e.currentTarget.childNodes[1].childNodes[5].childNodes[0].data;
        	var desc = e.currentTarget.childNodes[3].childNodes[0].data;
        	rcmdOb = {
        		id: id,
        		name: name
        	};
        	openRecommendationModal(name, desc.trim(), true);
        });
});

function openRecommendationModal(name, desc, isUpdateFlag) {
	$("#typeahead-item-name").val(name);
	$("#recommend-desc-area").val(desc);
	if (isUpdateFlag) {
		$("#removeRecommendation").removeClass('hide');
		$("#recommendSubmit").html('Update recommendation');
	} else {
		$("#removeRecommendation").addClass('hide');
		$("#recommendSubmit").html('Add recommendation');
	}
	$('#recommendModal').modal('show');
}

function submitRecommendation() {
	var $input = $('#typeahead-item-name');
	var current = $input.typeahead("getActive");
	if (current) {
	    // Some item from your model is active!
	    if (current.name == $input.val()) {
	      // This means the exact match is found. Use toLowerCase() if you want case insensitive match.
	      console.log('exact match');
	      console.log(current);		  
	    } else {
	      //console.log('partial match');
	    }
	  } else {
	    //console.log('no match');
	  }
}

function removeRecommendation() {
	$('#recommendModal').modal('hide');
	$("#alertText").html('Are you sure you want to remove ' + rcmdOb.name + ' from your recommendations ?')
	$("#alertHeading").html('Confirm action')
	$("#alertModal").modal('show');
}