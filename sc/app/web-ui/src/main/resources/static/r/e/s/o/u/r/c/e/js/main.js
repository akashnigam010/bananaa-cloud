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
    
    $('#login-button-mini').on('mouseup', function (e) {
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
		$('#login-dropdown-mini').addClass('hide');
		$('#loginModal').modal('show');
	} else {
		$('#login-dropdown').removeClass('hide');
		$('#login-dropdown-mini').removeClass('hide');
	}
}

function imageView() {
    var primaryImage = $("#primaryImageTemp").html();
    var primaryDetail = $(".primary-detail").html();
    var secondaryDetail = $(".secondary-detail").html();
    $("#imageViewModal").find('.detail-image').attr('src', primaryImage);
    $("#imageViewModal").find('.primary-detail').html(primaryDetail);
    $("#imageViewModal").find('.secondary-detail').html(secondaryDetail);
    $("#imageViewModal").modal('show');
}

function handleErrorCallback(response) {
	if (response.statusCodes != null) {
		  if (response.statusCodes.statusCode[0].code == '90004' ) {
			  	$("#login-info").html('Login');
	    		$('#login-dropdown').addClass('hide');
	    		$('#login-dropdown-mini').addClass('hide');
	    		$('#loginModal').modal('show');
		  } else {
			  alert('Something is not right, please try again after refreshing the page.');
		  }
	  }
}

function searchConfig(element) {
	var timeout;
	var config = {
    	minLength: 2,
    	autoSelect: true,
    	fitToElement: true,
    	matcher: function(merchant) {
            if (merchant.name.toLowerCase().includes(this.query.trim().toLowerCase())) {
                return true;
            } else {
                this.query = 'No match found';
                return true;
            }
        },
    	displayText: function(merchant) {
	        return '<div style="padding: 2%;"><span>' + 
	                  merchant.name + 
	                  '</span> <br /><span class="font-0-8">' +
	                  merchant.shortAddress + 
	                  '</span></div>';
		},
		afterSelect: function(merchant) {
			if (merchant.id != -999) {
				element.val(merchant.name);
            } else {
            	element.val('');
            }
        },
    	source: function(query, process) {
    		var that = this;
            if (timeout) {
                clearTimeout(timeout);
            }
            timeout = setTimeout(function() {
            	var dataOb = {
            			searchString : query
            	};
                return $.ajax({
                	method: "POST",
					url: "/socyal/merchant/searchMerchant",
					contentType : "application/json",
					data: JSON.stringify(dataOb),
					beforeSend: function() {
						that.$element.addClass('loading');
						that.$element.removeClass('noloading');
			        },
			        complete: function() {
			        	that.$element.removeClass('loading');
			        	that.$element.addClass('noloading');
			        }
              	})
              	  .done(function(response) {
              		  if (response.result) {
              			  if (response.merchants.length == 0) {
              				alert('Something went wrong. Please try again after refreshing the page');
              			  } else {
              				return process(response.merchants);
              			  }              			
              		  } else {
            			  handleErrorCallback(response);
            		  }	          		  
              	  });
            }, 300);
        },
        updater:function (merchant) {
        	if (merchant.id != -999) {
                window.location.href = "/" + merchant.merchantUrl;
            }
            return merchant;
        }
    };
	
	return config;
}

function loadLocations(e) {
	var isLocationUpdated = false;
    var defaultSelected = e.val();
    e.typeahead({
        fitToElement: true,
        showHintOnFocus: "all",
        items: 4,
        displayText: function(location) {
              return '<div style="padding: 2%; font-size: 0.9em;"><span>' + 
                        location.name + 
                        '</span></div>';
        },
        afterSelect: function(location) {
            e.val(location.name);
        },
        source: [
            {id: "1", cityId: 1, localityId: null, name: "All of Hyderabad"},
            {id: "2", cityId: 1, localityId: 1,  name: "Hitech City"},
            {id: "3", cityId: 1, localityId: 2,  name: "Jubilee Hills"},
            {id: "4", cityId: 1, localityId: 3,  name: "Banjara Hills"},
            {id: "5", cityId: 1, localityId: 4,  name: "Gachibowli"},
            {id: "6", cityId: 1, localityId: 5,  name: "Kondapur"}
        ],
        updater: function(location) {
            isLocationUpdated = true;
            defaultSelected = location.name;
            if (location.localityId != null) {
            	var dataOb = {
                		localityId : location.localityId
            	};
            	$.ajax({
              	  method: "POST",
              	  url: "/socyal/login/setLocation",
              	  contentType : "application/json",
              	  data: JSON.stringify(dataOb)
              	})
              	  .done(function(response) {
              		  if (!response.result) {
              			location.reload();
              		  }		
              	  });
            }
            return location;
        }
    });

    e.on('mousedown', function() {
        e.val('');
        e.typeahead('lookup');
    });

    e.on('focusout', function() {
        if (!isLocationUpdated) {
            e.val(defaultSelected);
            isLocationUpdated = false;
        }
    });
}