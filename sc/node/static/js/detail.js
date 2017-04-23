$(document).ready(function() {
	page = 'detail';
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");
    getMyRecommendations();
});

function loadPopularDishes() {
	var dataOb = {
			merchantId : merchantId,
			page : 1,
            resultsPerPage : 10
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/item/getPopularItems",
    	  contentType : "application/json",
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var popularItemsHtml = '';
    		  var subText = '';
    		  if (response.result) {
    			  if (response.items.length > 0) {
    				  for (var i=0; i<response.items.length; i++) {
    					  if (response.items[i].recommendations == 1) {
    						  subText = response.items[i].recommendations+' person recommends';
    					  } else {
    						  subText = response.items[i].recommendations+' people recommend';
    					  }    					  
    					  popularItemsHtml += 
    						  '<div class="row">'+
	    						  '<a class="cursor-pointer" href="'+response.items[i].itemUrl+'">'+
				                     '<div class="col-xs-12 recommended-item">'+
				                          '<div class="float-left" style="object-fit: cover;">'+
				                              '<img class="user-icon" src="'+response.items[i].thumbnail+'" />'+
				                          '</div>'+
				                          '<div class="float-left item-desc-wrapper">'+
				                              '<div class="bold item-name">'+
				                              		response.items[i].name+
				                              '</div>'+
				                               '<div>'+ subText +
				                              '</div>'+
				                          '</div>'+                                                                           
				                      '</div>'+
			                      '</a>'+
			                  '</div>';
    				  }
    				  $('.loadmore-wrapper').html(popularItemsHtml);
    				  $('#loadMoreModal').modal('show');
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function getMyRecommendations() {
	$('.recommended-wrapper').find('.loader').show();
	$('.my-recommendations').html('');
	var dataOb = {
			merchantId : merchantId,
			page : 1
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/recommendation/getMyRecommendations",
    	  contentType : "application/json",
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var myRecommendationsHtml = '';
    		  if (response.result) {
    			  if (response.recommendations.length > 0) {
    				  for (var i=0; i<response.recommendations.length; i++) {
    					  myRecommendationsHtml +=   '<div class="col-xs-12 recommended-item cursor-pointer my-recommended-item">'+
                                                           '<div class="float-left" style="object-fit: cover;">'+
                                                               '<img class="user-icon" src="'+response.recommendations[i].thumbnail+'" />'+
                                                           '</div>'+
                                                           '<div class="float-left item-desc-wrapper">'+
                                                                '<span class="hide recommendation-id">'+response.recommendations[i].id+'</span>'+
                                                                '<span class="hide item-id">'+response.recommendations[i].itemId+'</span>'+
                                                                '<div class="bold item-name">'+response.recommendations[i].name+'</div>'+
                                                                '<div class="light">'+
                                                                   getRecommendationHtml(response.recommendations[i].totalRcmdns)+
                                                                '</div>'+
                                                           '</div>'+
                                                           getDescriptionHtml(response.recommendations[i].description)+
                                                           '<div class="light float-left font-0-8">'+
                                                           		response.recommendations[i].timeDiff+
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

function getDescriptionHtml(desc) {
    if (desc != null) {
        return '<div class="col-xs-12 recommend-item-desc">'+desc+'</div>';
    } else {
        return '';
    }
}

function getRecommendationHtml(rcmdCount) {
    if (rcmdCount == 1) {
        return 'You recommend this';
    } else if (rcmdCount == 2) {
        return 'You and 1 other recommend this';
    } else {
        return 'You and '+(rcmdCount-1)+' others recommend this';
    }
}

function activateUpdateRcmdModal() {
	$(".my-recommended-item").off('mouseup');
	$(".my-recommended-item").on('mouseup', function(e){
		var review = '';
        if ($(this).find('.recommend-item-desc').html()) {
            review = $(this).find('.recommend-item-desc').html().trim();
        }
    	rcmdOb = {
    		rcmdId: $(this).find('.recommendation-id').html(),
    		itemId: $(this).find('.item-id').html(),
    		name: $(this).find('.item-name').html().trim(),
    		desc: review
    	};
    	openRecommendationModal(rcmdOb.rcmdId, rcmdOb.itemId, rcmdOb.name, rcmdOb.desc, true);
    });
}