$(document).ready(function() {
	page = 'index';
    if (isMobile) {
//        $('#search-field').on('touchstart', function (e) {
//            var minusHeight = $(".search-box-wrapper").height()+15;
//            $("html, body").animate({ scrollTop: ($('.home-banner').height()-minusHeight) }, 300);    
//        });
        $(document).on('touchstart', function (e) {
            if ($(e.target).closest(".search-box-suggestion").length === 0 && $('.search-box-suggestion').html() != '') {
                $(".search-box-suggestion").html('');
            }
        });
    } else {
        $(document).on('mousedown', function (e) {
            if ($(e.target).closest(".search-box-suggestion").length === 0 && $('.search-box-suggestion').html() != '') {
                $(".search-box-suggestion").html('');
            }
        });
    }
    
    $('#search-field').typeahead(searchConfig($('#search-field')));
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
    						  '<div class="trending-item">'+
			                              '<a href="/'+response.merchants[i].merchantUrl+'">'+
			                          '<div>'+
			                              '<img class="trend-image" src="'+response.merchants[i].thumbnail+'" alt=""/>'+
			                              '<div class="trending-item-desc">'+
			                                  '<div style="padding: 4%;">'+
			                                      '<p>'+
			                                          '<span class="bold font-1-3">'+response.merchants[i].name+'</span>'+
			                                          '<br />'+
			                                          '<span class="light">'+response.merchants[i].shortAddress+'</span>'+
			                                      '</p>'+
			                                      '<span>'+response.merchants[i].recommendations+' Food Recommendations</span>'+
			                                  '</div>'+
			                              '</div>'+
			                          '</div>'+
			                      '</a>'+
			                  '</div>';
    				  }
    				  $('.trending-restaurant-wrapper').html(trendingRestaurantHtml);
    				  addSlick($('.trending-restaurant-wrapper'));
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
    						  '<div class="trending-item">'+
			                              '<a href="'+response.stories[i].url+'">'+
			                          '<div class="diary-sec">'+
			                              '<img class="trend-image diary-img" src="'+response.stories[i].imageUrl+'" alt="" />'+
			                              '<div class="align-center padding" style="position: absolute; top: 20%; width: 100%;">'+
			                                  '<p class="bold font-1-3">'+response.stories[i].name+'</p>'+
			                              '</div>'+
			                          '</div>'+
			                      '</a>'+
			                  '</div>';
    				  }
    				  $('.diary-wrapper').html(storiesHtml);
    				  addSlick($('.diary-wrapper'));
    				  $('.stories').find('.loader').hide();
    			  }
    		  } else {
    			  handleErrorCallback(response);
    		  }
    	  });
}

function addSlick(slickElement) {
	slickElement.slick({
        dots: false,
        infinite: false,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 4,
        responsive: [
            {
              breakpoint: 2048,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 1
              }
            },
            {
              breakpoint: 1024,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 1
              }
            },
            {
              breakpoint: 480,
              settings: {
                arrows: false,
                slidesToShow: 1.5,
                slidesToScroll: 1
              }
            }
        ]
    });
}