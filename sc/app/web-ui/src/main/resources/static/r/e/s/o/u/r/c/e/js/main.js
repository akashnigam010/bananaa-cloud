var isMobile = false;
var fs = [];
var merchantId;
var merchantName;
var itemId;
var itemName;
var page;
var localityArr = [
                   {localityId: 'hyderabad', cityId: null, name: "Hyderabad"},
                   {localityId: 'hitech-city', cityId: 'hyderabad',  name: "Hitech City"},
                   {localityId: 'jubilee-hills', cityId: 'hyderabad',  name: "Jubilee Hills"},
                   {localityId: 'banjara-hills', cityId: 'hyderabad',  name: "Banjara Hills"},
                   {localityId: 'gachibowli', cityId: 'hyderabad',  name: "Gachibowli"},
                   {localityId: 'kondapur', cityId: 'hyderabad',  name: "Kondapur"}
               ];
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
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 4));
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
    $('[data-toggle="popover"]').popover();
    $("#topSearchInput").keypress(function(e) {
        if(e.which == 10 || e.which == 13) {
            homeGlobalSearch();
        }
    });
});

function homeGlobalSearch() {
    var urlWithParams = window.location.href;
    var urlWithOutParams = urlWithParams.split('?')[0];
    var searchString = $("#topSearchInput").val();
    window.location = urlWithOutParams+'?search='+searchString;
}

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

function searchConfig(element, loc) {
	var timeout;
	var config = {
    	minLength: 2,
    	autoSelect: false,
    	fitToElement: true,
    	matcher: function(searchItem) {
            if (searchItem.name.toLowerCase().includes(this.query.trim().toLowerCase())) {
                return true;
            } else {
                this.query = 'No match found';
                return true;
            }
        },
    	displayText: function(searchItem) {
    		var displayTxt = '<div style="padding: 2%;"><span>' + 
								searchItem.name + 
							 '</span> <br />';
    		if (searchItem.type == 'RESTAURANT') {
    			displayTxt += '<span class="font-0-8">' +
                				searchItem.shortAddress + 
                			  '</span></div>';
    		} else if (searchItem.type == 'CUISINE' || searchItem.type == 'DISH') {
    			displayTxt += '<span class="font-0-8">' +
    							searchItem.type + 
							  '</span></div>';
    		}
	        return displayTxt;
		},
		afterSelect: function(searchItem) {
			if (searchItem.type != null) {
				element.val(searchItem.name);
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
					url: "/socyal/merchant/gSearch",
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
              			  return process(response.searchItems);             			
              		  } else {
            			  handleErrorCallback(response);
            		  }	          		  
              	  });
            }, 300);
        },
        updater:function (searchItem) {
        	if (searchItem.type != null) {
        		if (searchItem.type == 'RESTAURANT') {
        			window.location.href = searchItem.merchantUrl;
        		} else if (searchItem.type == 'CUISINE' || searchItem.type == 'DISH') {
        			var l = getLocationUrlString(loc.val());
        			window.location.href = '/' + l + '/' + searchItem.nameId;
        		}
        	}
            return searchItem;
        }
    };
	
	return config;
}

function getLocationUrlString(val) {
	var l = '';
	for (var i=0; i<localityArr.length; i++) {
		if (localityArr[i].name === val) {
			if (localityArr[i].cityId != null) {
				l = localityArr[i].cityId + '/' + localityArr[i].localityId;
			} else {
				l = localityArr[i].localityId;
			}
			break;
		}
	}
	return l;
}

function loadLocations(e, searchElement) {
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
        source: localityArr,
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
            setTimeout(function() {
            	searchElement.focus();
            }, 100);
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