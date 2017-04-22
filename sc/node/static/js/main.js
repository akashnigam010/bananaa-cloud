var isMobile = false;
var rcmdOb = {};
var fs = [];
var merchantId;
var merchantName;
var itemId;
var itemName;
var page;
$(document).ready(function() {
	merchantId = $('#merchantId').val();
	merchantName = $('#merchantName').val();
	itemId = $('#itemId').val();
	itemName = $('#itemName').val();
	isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
    window.onresize = function(event) {
        isMobile = window.matchMedia("only screen and (max-width: 480px)").matches;
//        $('#search-field').on('touchstart', function (e) {
//            // do not use 'click'; it fires 2 events - mouseup and mousedown/touchstart and touchend
//            var minusHeight = $(".search-box-wrapper").height()+15;
//            $("html, body").animate({ scrollTop: ($('.home-banner').height()-minusHeight) }, 300);    
//        });
    };
    
    $('#login-button').on('mouseup', function (e) {
    	activateLogin();
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
	$('.modal').on('hidden.bs.modal', function(e) {
		if(window.location.hash == "#modal") {
			window.history.back();
		}
	});
    $(window).on('hashchange', function (event) {
        if(window.location.hash != "#modal") {
            $('.modal').modal('hide');
        }
    });
});

function activateLogin() {
	if ($("#login-info").html() == 'Login') {
		$('#login-dropdown').addClass('hide');
		$('#loginModal').modal('show');
	} else {
		$('#login-dropdown').removeClass('hide');
	}
}

function handleErrorCallback(response) {
	if (response.statusCodes != null) {
		  if (response.statusCodes.statusCode[0].code == '90004' ) {
			  	$("#login-info").html('Login');
	    		$('#login-dropdown').addClass('hide');
	    		$('#loginModal').modal('show');
		  } else {
			  alert('Something is not right, please try again after refreshing the page.');
		  }
	  }
}