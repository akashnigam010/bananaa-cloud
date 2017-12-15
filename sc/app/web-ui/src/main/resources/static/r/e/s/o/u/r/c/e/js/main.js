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
//                   {localityId: 'banjara-hills', cityId: 'hyderabad',  name: "Banjara Hills"},
                   {localityId: 'gachibowli', cityId: 'hyderabad',  name: "Gachibowli"}
//                   {localityId: 'kondapur', cityId: 'hyderabad',  name: "Kondapur"}
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
    
    $('.downloadPlayStoreBtn').on('mouseup', function (e) {
        window.location = "https://play.google.com/store/apps/details?id=in.bananaa&hl=en";
    });
    
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
            homeGlobalSearch($("#topSearchInput").val());
        }
    });
    
    $("#modalSearchInput").keypress(function(e) {
        if(e.which == 10 || e.which == 13) {
        	homeGlobalSearch($("#modalSearchInput").val());
        }
    });
    
    $("#topHeaderSearchButton").on('click', function() {
    	homeGlobalSearch($("#topSearchInput").val());
    });
});

function homeGlobalSearch(searchString) {
	if (searchString == '') {
    	searchString = 'all';
    }
    var urlWithParams = window.location.href;
    var urlWithOutParams = urlWithParams.split('?')[0];
    var urlWithOutHash = urlWithOutParams.split('#')[0];
    window.location = urlWithOutHash+'?search='+searchString;
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
		  } else if (response.statusCodes.statusCode[0].code == '10603' ) {
			  alert(response.statusCodes.statusCode[0].description);
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
    		var displayTxt = '<div style="padding: 1%;"><span>' + 
								searchItem.name + 
							 '</span> <br />';
    		if (searchItem.type == 'RESTAURANT') {
    			displayTxt += '<span class="font-0-8">' +
                				searchItem.shortAddress + 
                			  '</span></div>';
    		} else if (searchItem.type == 'CUISINE') {
    			displayTxt += '<span class="font-0-8">' +
    							searchItem.type + 
							  '</span></div>';
    		} else if (searchItem.type == 'SUGGESTION') {
    			displayTxt += '<span class="font-0-8">' +
				'DISH' + 
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
        		} else if (searchItem.type == 'CUISINE' || searchItem.type == 'SUGGESTION') {
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

function manualLogin() {
    var email = $("#m_email").val(),
    password = $("#m_password").val();

    if (!validateEmail(email)) {
        addError('Invalid email entered');
    } else if (!password) {
        addError('Please enter your password');
    } else {
        var dataOb = {
			email : email,
			password : password
    	};
    	$.ajax({
      	  method: "POST",
      	  url: "/socyal/login/mLogin",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			addSuccess('Logged in successfully');
  				location.reload();
      		  } else {
      			handleErrorResponseLoginModal(response.statusCodes.statusCode[0].description);
      		  }		
      	  });
    }
}

function register() {
    var name = $("#r_name").val(),
    email = $("#r_email").val(),
    password = $("#r_password").val();

    if (!name) {
        addError('Please enter your name');
    } else if (!validateEmail(email)) {
        addError('Invalid email entered');
    } else if (!password) {
        addError('Please enter a password');
    } else {
        var dataOb = {
			name : name,
			email : email,
			password : password
    	};
    	$.ajax({
      	  method: "POST",
      	  url: "/socyal/login/register",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
  			  	addSuccess('Successfully Registered');
  				location.reload();
      		  } else {
      			handleErrorResponseLoginModal(response.statusCodes.statusCode[0].description);
      		  }		
      	  });
    }
}

function forgotPassword() {
    var email = $("#f_email").val();
    if (!validateEmail(email)) {
        addError('Invalid email entered');
    } else {
        var dataOb = {
			email : email
    	};
    	$.ajax({
      	  method: "POST",
      	  url: "/socyal/login/resetPassword",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			addSuccess('We have emailed you the password. Please check your mail box');
      		  } else {
      			handleErrorResponseLoginModal(response.statusCodes.statusCode[0].description);
      		  }		
      	  });
    }
}

function subscribe() {
    var email = $("#subscribeMail").val();
    if (!validateEmail(email)) {
        $("#subscriptionError").html("Please check your email");
        $("#subscriptionError").attr('style', 'color: red;')
        $("#subscriptionError").removeClass('hide');
    } else {
    	var dataOb = {
    	        name : 'Subscribe',
    	        phone : 'Subscribe',
    	        email : email,
    	        message : 'Subscribe'
    	    };
    	    $.ajax({
    	          method: "POST",
    	          url: "/socyal/management/sendMessage",
    	          contentType : "application/json",
    	          data: JSON.stringify(dataOb)
    	        })
    	          .done(function(response) {
    	        	  if (response.result) {
    	            	  $("#subscribeMail").val('');
    	            	  $("#subscriptionError").html("Thank you!");
    	                  $("#subscriptionError").attr('style', 'color: green;')
    	                  $("#subscriptionError").removeClass('hide');
    	              } else {
    	                  handleErrorCallback(response);
    	              }                       
    	          });
    }
}

function handleErrorResponseLoginModal(desc) {
	addError(desc);
	$("#login-info").html('Login');
	$('#login-dropdown').addClass('hide');
	$('#login-dropdown-mini').addClass('hide');
	$('#loginModal').modal('show');
	$("#loginModal").find(".loader").addClass('hide');
	$("#loginModal").find(".modal-body").removeClass('hide');
}

function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

function addError(msg) {
    $(".login-title").addClass('hide');
    $(".login-success").addClass('hide');
    $(".login-error").html(msg);
    $(".login-error").removeClass('hide');
    setTimeout(function(){
		$(".login-error").addClass('hide');
		$(".login-title").removeClass('hide');
	}, 3000);
}

function addSuccess(msg) {
    $(".login-title").addClass('hide');
    $(".login-error").addClass('hide');
    $(".login-success").html(msg);
    $(".login-success").removeClass('hide');
    setTimeout(function(){
		$(".login-success").addClass('hide');
		$(".login-title").removeClass('hide');
	}, 10000);
}