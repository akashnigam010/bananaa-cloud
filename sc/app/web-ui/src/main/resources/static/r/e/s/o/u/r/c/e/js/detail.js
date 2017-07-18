$(document).ready(function() {
	page = 'detail';
    var primaryImage = $("#primaryImageTemp").html();
    var background = "url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");
    getMyRecommendations();
    $('#topSearchInput').typeahead(searchConfig($('#topSearchInput'), $('#topSearchLocation')));
    $('#modalSearchInput').typeahead(searchConfig($('#modalSearchInput'), $('#modalSearchLocation')));
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
    						  subText = response.items[i].recommendations+' person rated';
    					  } else {
    						  subText = response.items[i].recommendations+' people rated';
    					  }    					  
    					  popularItemsHtml += 
    						  '<div class="row">'+
	    						  '<a class="cursor-pointer" href="'+response.items[i].itemUrl+'">'+
				                     '<div class="col-xs-12 recommended-item">'+
				                          '<div class="float-left" style="object-fit: cover; width: 15%;">'+
				                              '<img class="user-icon" src="'+response.items[i].thumbnail+'" />'+
				                          '</div>'+
				                          '<div class="float-left item-desc-wrapper">'+
				                              '<div class="bold item-name">'+
				                              		response.items[i].name+
				                              '</div>'+
				                               '<div>'+ subText +
				                              '</div>'+
				                          '</div>'+
					                      '<div class="float-right" style="width: 10%; padding-top: 2.5%;">'+
	                                          '<span class="float-right rating-rcmd ' + response.items[i].ratingClass + '" style="padding: 0 20%;">'+
	                                          getRating(response.items[i].rating) +
	                                          '</span>'+
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

function getRating(rating) {
	if (rating == '') {
		return '-.-';
	} else {
		return rating;
	}
	
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
                                                           '<div class="float-left" style="object-fit: cover; width: 15%;">'+
                                                               '<img class="user-icon" src="'+response.recommendations[i].thumbnail+'" />'+
                                                           '</div>'+
                                                           '<div class="float-left item-desc-wrapper">'+
                                                                '<span class="hide item-id">'+response.recommendations[i].itemId+'</span>'+
                                                                '<div class="bold item-name">'+response.recommendations[i].name+'</div>'+
                                                                '<span class="hide rating">'+response.recommendations[i].rating+'</span>'+
                                                                '<span class="hide merchant-name">'+response.merchantName+'</span>'+
                                                                '<div class="light">'+
                                                                   getRecommendationHtml(response.recommendations[i].totalRcmdns)+
                                                                '</div>'+
                                                           '</div>'+
                                                            getDescriptionHtml(
																	response.recommendations[i].description,
																	response.recommendations[i].rating,
																	response.recommendations[i].ratingClass)
															+
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

function getDescriptionHtml(desc, rating, ratingClass) {
    if (desc != null) {
    	return '<div class="col-xs-12 recommend-item-desc">'+
					'<span class="bold">Rated &nbsp;</span>'+
					'<span class="rating-rcmd '+ ratingClass +'">'+
						rating +
					'</span> &nbsp; &nbsp;'+
					'<span class="review">'+
						desc +
					'</span>'+
				'</div>';
    } else {
        return '<div class="col-xs-12 recommend-item-desc">'+
        			'<span class="bold">Rated &nbsp;</span>'+
        			'<span class="rating-rcmd '+ ratingClass +'">'+
        				rating +
    				'</span> &nbsp; &nbsp;'+
    				'<span class="review">'+
						desc +
					'</span>'+
    			'</div>';
    }
}

function getRecommendationHtml(rcmdCount) {
    if (rcmdCount == 1) {
        return 'You rated this';
    } else if (rcmdCount == 2) {
        return 'You and 1 other rated this';
    } else {
        return 'You and '+(rcmdCount-1)+' others rated this';
    }
}

function activateUpdateRcmdModal() {
	$(".my-recommended-item").off('mouseup');
	$(".my-recommended-item").on('mouseup', function(e){
		var review = '';
        if ($(this).find('.review').html()) {
            review = $(this).find('.review').html().trim();
        }
    	rcmdOb = {
    		id: $(this).find('.item-id').html(),
    		name: $(this).find('.item-name').html().trim(),
    		merchantName: $(this).find('.merchant-name').html().trim(),
    		rating: parseInt($(this).find('.rating').html().trim()),
    		review: review
    	};
    	openRecommendationModal(rcmdOb);
    });
}