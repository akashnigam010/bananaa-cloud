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
			itemId : ItemId,
			page : 1
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/recommendation/getMyItemRecommendation",
    	  contentType : "application/json",
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var myRecommendationsHtml = '';
    		  if (response.result) {
    			  if (response.recommendations.length > 0) {
    				  for (var i=0; i<response.recommendations.length; i++) {
    					  myRecommendationsHtml += 
    						  '<div class="row">'+
		                          '<div class="col-xs-12 recommended-item cursor-pointer">'+
		                              '<div class="bold recommend-item-name">'+
		                                  '<span class="hide recommendation-id">'+response.recommendations[i].id+'</span>'+
		                              	  '<span class="hide item-id">'+response.recommendations[i].itemId+'</span>'+
		                                  '<span class="bna-color item-hash">#'+(i+1)+'</span>'+
		                                  '&nbsp; <span class="item-name">'+response.recommendations[i].name+'</span>'+
		                              '</div>'+
		                              '<div class="recommend-item-desc">'+
		                              	(response.recommendations[i].description!=null?response.recommendations[i].description:'')+
		                              '</div>'+                                                                         
		                          '</div>'+
		                      '</div>';
    				  }
    				  $('.my-recommendations').html(myRecommendationsHtml);
    				  activateUpdateRcmdModal();
    			  }
    			  $('.recommended-wrapper').find('.loader').hide();
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