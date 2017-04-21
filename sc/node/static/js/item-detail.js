$(document).ready(function() {
	page = 'item-detail';
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");
    getMyItemRecommendation();
});

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
    				  $("#recommendation-id").html(response.recommendation.id);
    				  $("#item-id").html(response.recommendation.itemId);
    				  $("#item-name").html(response.recommendation.name);
    				  if (response.recommendation.description == '') {
    					  $("#no-review-text").removeClass('hide');
    				  } else {
    					  $("#no-review-text").addClass('hide');    					  
    				  }
    				  $("#recommend-item-desc").html(response.recommendation.description);
    				  $("#addItemRecommendButton").addClass('hide');
    				  $("#recommend-message").addClass('hide');
    				  activateUpdateRcmdModal();
    			  } else {
    				  $("#recommend-item-desc").html('');
    				  $("#addItemRecommendButton").removeClass('hide');
    				  $("#recommend-message").removeClass('hide');
    				  $("#no-review-text").addClass('hide');
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function activateUpdateRcmdModal() {
	$(".my-item-recommendation").on('mouseup', function(e){
    	rcmdOb = {
    		rcmdId: $(this).find('#recommendation-id').html(),
    		itemId: $(this).find('#item-id').html(),
    		name: $(this).find('#item-name').html(),
    		desc: $(this).find('#recommend-item-desc').html().trim()
    	};
    	openRecommendationModal(rcmdOb.rcmdId, rcmdOb.itemId, rcmdOb.name, rcmdOb.desc, true);
    });
}