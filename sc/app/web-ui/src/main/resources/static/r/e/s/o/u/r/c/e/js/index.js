$(document).ready(function() {
	page = 'index';
	if (isMobile) {
        $('#search-field, #search-location').on('mousedown', function (e) {
        	$('html, body').animate({
                scrollTop: $("#search-field").offset().top - 65
            }, 200);
        });
    }
    
    $('#search-field').typeahead(searchConfig($('#search-field'), $('#search-location')));
    loadLocations($('#search-location'));
    getTrendingRestaurants();
    getStories();
});

function getTrendingRestaurants() {
	$.ajax({
    	  method: "GET",
    	  url: "/socyal/merchant/getTrendingRestaurants",
    	  contentType : "application/json"
    	})
    	  .done(function(response) {
    		  var trendingRestaurantHtml = '';
    		  if (response.result) {
    			  if (response.merchants.length > 0) {
    				  for (var i=0; i<response.merchants.length; i++) {    					  
    					  trendingRestaurantHtml +=
    						  '<div class="flick-div" onclick="javascript:location.href=\''+response.merchants[i].merchantUrl+'\'">'+
	                              '<img class="flick-image flick-img-txt" src="'+response.merchants[i].thumbnail+'" alt="'+response.merchants[i].name+' at Bananaa" />'+
	                                  '<div class="flick-txt padding-left">'+
	                                      '<span class="bold font-1-3">'+response.merchants[i].name+'</span>'+
	                                      '<br />'+
	                                      '<span class="light font-0-8">'+response.merchants[i].shortAddress+'</span>'+
	                                  '</div>'+
	                              '</div>';
    				  }
    				  $('.trending-wrapper').html(trendingRestaurantHtml);
    				  $('.trending-wrapper').flickity(getFlickityOptions());
    				  $('.trending-restaurants').find('.loader').hide();
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function getStories() {
	$.ajax({
    	  method: "GET",
    	  url: "/socyal/merchant/getStories",
    	  contentType : "application/json"
    	})
    	  .done(function(response) {
    		  var storiesHtml = '';
    		  if (response.result) {
    			  if (response.stories.length > 0) {
    				  for (var i=0; i<response.stories.length; i++) {
    					  storiesHtml +=
    						  '<div class="flick-div" onclick="javascript:location.href=\''+response.stories[i].url+'\'">'+
		                          '<img class="flick-image diary-img" src="'+response.stories[i].imageUrl+'" alt="'+response.stories[i].name+' Story at Bananaa" />'+
		                          '<div class="align-center padding" style="position: absolute; top: 20%; width: 100%;">'+
		                              '<p class="bold font-1-3">'+response.stories[i].name+'</p>'+
		                          '</div>'+
		                      '</div>';
    				  }
    				  $('.diary-wrapper').html(storiesHtml);
    				  $('.diary-wrapper').flickity(getFlickityOptions());
    				  $('.stories').find('.loader').hide();
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function getFlickityOptions() {
    var options = {
        cellAlign: 'left',
        contain: true,
        freeScroll: true,
        pageDots: false,
        friction: 0.2,
        prevNextButtons: true
    };
    if (isMobile) {
        options.prevNextButtons = false;
    }
    return options;
}