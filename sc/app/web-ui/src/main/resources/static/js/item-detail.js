$(document).ready(function() {
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
    			  if (response.recommended) {
    				  console.log('i recommend');
    			  } else {
    				  console.log('i havent recommended yet');
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function activateUpdateRcmdModal() {
	$(".recommended-item").on('mouseup', function(e){
    	rcmdOb = {
    		rcmdId: $(this).find('.recommendation-id').html(),
    		itemId: $(this).find('.item-id').html(),
    		name: $(this).find('.item-name').html(),
    		desc: $(this).find('.recommend-item-desc').html().trim()
    	};
    	openRecommendationModal(rcmdOb.rcmdId, rcmdOb.itemId, rcmdOb.name, rcmdOb.desc, true);
    });
}