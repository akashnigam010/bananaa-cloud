$(document).ready(function() {
	
    if (isMobile) {
        $('#search-field').on('touchstart', function (e) {
            var minusHeight = $(".search-box-wrapper").height()+15;
            $("html, body").animate({ scrollTop: ($('.home-banner').height()-minusHeight) }, 300);    
        });
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
	
    var timerid;
    accessToken = $('#accessToken').val();
    $("#search-field").on("input",function(e){
        var value = $(this).val();
        if($(this).data("lastval")!= value){

            $(this).data("lastval",value);        
            clearTimeout(timerid);

            timerid = setTimeout(function() {
                //change action
            	var dataOb = {
            			searchString : $("#search-field").val()
            	};
                $.ajax({
                	  method: "POST",
                	  url: "/socyal/merchant/searchMerchant",
                	  contentType : "application/json",
                	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
                	  data: JSON.stringify(dataOb)
                	})
                	  .done(function(response) {
                		  var sugestionHtml = '';
                		  if (response.merchants.length == 0) {
                			  sugestionHtml += 	'<div class="col-xs-12 suggestion-wrapper">'+
	                			'<p class="suggestion" align="left">'+
	                			'<b>Oops, no results found</b>'+
	                			'</p></div>';
                		  } else {
                			  for (var i=0; i < response.merchants.length; i++) {
                    			  sugestionHtml += 	'<div class="col-xs-12 suggestion-wrapper">'+
    					                			'<a href="'+ response.merchants[i].merchantUrl +'" >'+
    					                			'<p class="suggestion" align="left">';
                    			  sugestionHtml += '<b>' + response.merchants[i].name + "</b>, " + response.merchants[i].shortAddress;
                    			  if ( i == (response.merchants.length*1)-1) {
                    				  sugestionHtml += '</p></a></div>';
                    			  } else {
                    				  sugestionHtml += '</p></a><hr /></div>';
                    			  }
                    		  }
                		  }    
                		  $('.search-box-suggestion').html(sugestionHtml);
                	  });
            },500);
        };
    });
    
    getTrendingRestaurants();
    getTrendingFood();
    
});

function getTrendingRestaurants() {
	$.ajax({
    	  method: "GET",
    	  url: "/socyal/merchant/getTrendingRestaurants",
    	  contentType : "application/json"
    	  //beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); }
    	})
    	  .done(function(response) {
    		  var trendingRestaurantHtml = '';
    		  if (response.result == true) {
    			  if (response.merchants.length > 0) {
    				  for (var i=0; i<response.merchants.length; i++) {
    					  trendingRestaurantHtml += 
    						  '<div class="trending-item">'+
			                              '<a href="'+response.merchants[i].merchantUrl+'">'+
			                          '<div>'+
			                              '<img class="trend-image" src="'+response.merchants[i].imageUrl+'" alt=""/>'+
			                              '<div class="trending-item-desc">'+
			                                  '<div style="padding: 4%;">'+
			                                      '<p>'+
			                                          '<span class="bold font-1-3">'+response.merchants[i].name+'</span>'+
			                                          '<br />'+
			                                          '<span class="light">'+response.merchants[i].shortAddress+'</span>'+
			                                      '</p>'+
			                                      '<span>320 Recommendations</span>'+
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
    		  }
    	  });
}

function getTrendingFood() {
	addSlick($('.trending-food-wrapper'));
}