var accessToken = '';
var isMobile = false;
$(document).ready(function() {
	accessToken = $('#accessToken').val();
	isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
    window.onresize = function(event) {
        isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
        $('#search-field').on('touchstart', function (e) {
            // do not use 'click'; it fires 2 events - mouseup and mousedown/touchstart and touchend
            var minusHeight = $(".search-box-wrapper").height()+15;
            $("html, body").animate({ scrollTop: ($('.home-banner').height()-minusHeight) }, 300);    
        });
    };
    
    $('#login-button').on('mouseup', function (e) {
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
    
    $('.modal').on('show.bs.modal', function(e) {
        window.location.hash = "modal";
    });
    // $('.modal').on('hidden.bs.modal', function(e) {
    // 	if(window.location.hash == "#modal") {
    //         window.history.back();
    //     }    	
    // });
    $(window).on('hashchange', function (event) {
        if(window.location.hash != "#modal") {
            $('.modal').modal('hide');
        }
    });
});

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