$(document).ready(function() {
	page = 'item-detail';
    var primaryImage = $("#primaryImageTemp").html();
    var background = "url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");
    $('#topSearchInput').typeahead(searchConfig($('#topSearchInput'), $('#topSearchLocation')));
    $('#modalSearchInput').typeahead(searchConfig($('#modalSearchInput'), $('#modalSearchLocation')));
    getMyItemRecommendation();
});

var item = {};

function getMyItemRecommendation() {
	var dataOb = {
			itemId : itemId
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/recommendation/getMyItemRecommendation",
    	  contentType : "application/json",
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var myItemRecommendationHtml = '';
    		  if (response.result) {
    			  $(".my-item-recommendation-wrapper").find('.loader').addClass('hide');
    			  $(".my-item-recommendation-section").removeClass('hide');
    			  if (response.recommended) {
    				  if (response.recommendation.rating == '' || response.recommendation.rating == null) {
    					  $("#rating-sec").addClass('hide');
    				  } else {
    					  item.rating = response.recommendation.rating;
    					  $("#rating-sec").removeClass('hide');
    					  $("#rating-val").html(response.recommendation.rating)
    				  }
    				  
    				  if (response.recommendation.description == '' || response.recommendation.description == null) {
    					  $("#recommend-item-desc").addClass('hide');
    				  } else {
    					  item.review = response.recommendation.description;
    					  $("#recommend-item-desc").removeClass('hide');
    					  $("#recommend-item-desc").html(response.recommendation.description);
    				  }    				  
    				  $("#recommend-message").addClass('hide');
    				  $("#recommend-time").html(response.recommendation.timeDiff);
    				  $("#recommend-time").removeClass('hide');
    			  } else {
    				  $("#rating-sec").addClass('hide');
    				  $("#recommend-item-desc").addClass('hide');
    				  $("#recommend-time").addClass('hide');
    				  $("#recommend-message").removeClass('hide');
    			  }
    			  activateUpdateRcmdModal();
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function activateUpdateRcmdModal() {
	$("#addItemRecommendButton").on('mouseup', function(e){
		if ($("#loginStatus").html() == 'false') {
    		activateLogin();
    		return;
		}
		rcmdOb = {
    		id: itemId,
    		name: itemName,
    		merchantName: merchantName,
    		review: item.review
    	};
		if (item.rating != '' && item.rating != null) {
			rcmdOb.rating = parseInt(item.rating);
		}
    	openRecommendationModal(rcmdOb);
    });
}