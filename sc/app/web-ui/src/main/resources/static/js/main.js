$(document).ready(function() {
	
	var isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
    window.onresize = function(event) {
        isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
    };
	
    var timerid;
    var accessToken = $('#accessToken').val();
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
    					                			'<a href="'+ $('#city').val() + "/" +response.merchants[i].id +'" >'+
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
    
    $('#login-button').click(function() {
    	if (isLoggedIn == false) {
    		console.log('user not logged in');
    		$('#login-dropdown').addClass('hide');
    		$('#loginModal').modal('show');
    	} else {
    		$('#login-dropdown').removeClass('hide');
    		console.log('user already logged in, not opening modal');
    	} 	
    });
    
    function repositionModal() {
        var modal = $(this),
            dialog = modal.find('.modal-dialog');
        modal.css('display', 'block');
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
    }
    $('.modal').on('show.bs.modal', repositionModal);
    $(window).on('resize', function() {
        $('.modal:visible').each(repositionModal);
    });
    
    $('#search-field').click(function() {
        if (isMobile) {
            var minusHeight = $(".search-box-wrapper").height()+15;
            $("html, body").animate({ scrollTop: ($('.home-banner').height()-minusHeight) }, 300);    
        }
    });
    
    $('.slick-class').slick({
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
    
});