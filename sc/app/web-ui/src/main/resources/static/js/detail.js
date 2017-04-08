var rcmdOb = {};
$(document).ready(function() {
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");
    
    var timeout;
    $('#modal-item-name').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	//showHintOnFocus: true,
    	source: function(query, process) {
            if (timeout) {
                clearTimeout(timeout);
            }
            timeout = setTimeout(function() {
            	var dataOb = {
            			searchString : query
            	};
                return $.ajax({
              	  method: "POST",
              	  url: "/socyal/item/searchItems",
              	  contentType : "application/json",
              	  data: JSON.stringify(dataOb)
              	})
              	  .done(function(response) {
              		  if (response.result) {
              			return process(response.items);
              		  } else {
            			  handleErrorCallback(response);
            		  }	          		  
              	  });
            }, 500);
        }
//        source: function (query, process) {
//        	var dataOb = {
//        			searchString : query
//        	};
//            return $.ajax({
//          	  method: "POST",
//          	  url: "/socyal/item/searchItems",
//          	  contentType : "application/json",
//          	  data: JSON.stringify(dataOb)
//          	})
//          	  .done(function(response) {
//          		  if (response.result) {
//          			return process(response.items);
//          		  } else {
//        			  handleErrorCallback(response);
//        		  }	          		  
//          	  });
//        }
    });
        
    $('#addRecommendButton').on('mouseup', function (e) {
    	openRecommendationModal('', '', '', '', false);
    });
    
    $('#modal-item-desc').on('focusin', function(e) {
    	$(this).attr('placeholder', 'Dive down into the specifics! Tell us how this dish or drink stands out to be a recommendation. And remember to keep it short - a minimum of 50 and a maximum of 200 characters. :)');
    });
    $('#modal-item-desc').on('focusout', function(e) {
    	$(this).attr('placeholder', '');
    });
    
    getMyRecommendations(1);
});

function loadPopularDishes() {
	accessToken = $('#accessToken').val();
	var dataOb = {
			merchantId : 1,
			page : 1
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/recommendation/getPopularItems",
    	  contentType : "application/json",
    	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var popularItemsHtml = '';
    		  if (response.result) {
    			  if (response.items.length > 0) {
    				  for (var i=0; i<response.items.length; i++) {
    					  popularItemsHtml += 
    						  '<div class="row">'+
	    						  '<a class="cursor-pointer" href="/hyderabad/fusion-9-hitech-city/12346">'+
				                     '<div class="col-xs-12 recommended-item">'+
				                          '<div class="float-left" style="object-fit: cover;">'+
				                              '<img class="user-icon" src="'+response.items[i].imageUrl+'" />'+
				                          '</div>'+
				                          '<div class="float-left item-desc-wrapper">'+
				                              '<div class="bold item-name">'+
				                              		response.items[i].name+
				                              '</div>'+
				                               '<div>'+
				                               		response.items[i].recommendations+' people recommended'+
				                              '</div>'+
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

function openRecommendationModal(rcmdId, itemId, name, desc, isUpdateFlag) {
	$("#recommendModal").find('.main-area').show();
	$("#recommendModal").find('.loader').addClass('hide');
	$("#modal-recommendation-id").val(rcmdId);
	$("#modal-item-id").val(itemId);
	$("#modal-item-name").val(name);
	$("#modal-item-desc").val(desc);
	if (isUpdateFlag) {
		$("#removeRecommendation").removeClass('hide');
		$("#recommendSubmit").html('Update recommendation');
	} else {
		$("#removeRecommendation").addClass('hide');
		$("#recommendSubmit").html('Add recommendation');
	}
	$('#recommendModal').find('.error-label').addClass('hide');
	$('#recommendModal').modal('show');
}

function submitRecommendation() {
	var nameInput = $('#modal-item-name');	
	var $rcmdId = $('#modal-recommendation-id').val();
	var $itemId = $('#modal-item-id').val();
	var $name = nameInput.val();
	var $desc = $('#modal-item-desc').val();
	var current = nameInput.typeahead("getActive");
	if (current) {
		if (current.name.toLowerCase() == $name.toLowerCase()) {
			$('#recommendModal').find('.error-label-name').addClass('hide');
			if (handleReview($desc)) {
				$("#recommendModal").find('.main-area').hide();
				$("#recommendModal").find('.loader').removeClass('hide');
			}	      	
	    } else {
	    	handlePartialMatch($rcmdId, $itemId, $name, $desc);
	    }
	  } else {
	  		handlePartialMatch($rcmdId, $itemId, $name, $desc);
	  }
}

function handlePartialMatch(rcmdId, itemId, name, desc) {
	if (name === rcmdOb.name && desc === rcmdOb.desc) {
		$('#recommendModal').modal('hide');
	}
	if (name === rcmdOb.name) {
		if (desc != rcmdOb.desc) {
			if (handleReview($desc)) {
				console.log('update this recommendation\'s review  - rcmdId: ' + rcmdId);	
			}
			return;	
		}
	} else {
		$('#recommendModal').find('.error-label-name').removeClass('hide');
	}	
}

function handleReview(desc) {
	if (desc.length == 0) {
		return true;
	}
	if(desc.length < 50 || desc.length > 200) {
		$('#recommendModal').find('.error-label-review').removeClass('hide');
		return false;
	}
	return true;
}

function removeRecommendation() {
	$('#recommendModal').modal('hide');
	$('#recommendModal').on('hidden.bs.modal', function() {
		$("#alertModal").find('.main-area').show();
		$("#alertModal").find('.loader').addClass('hide');
		$("#alertText").html('Are you sure you want to remove ' + rcmdOb.name + ' from your recommendations ?');
		$("#alertModal").modal('show');
		$("#confirmAlertButton").on('mouseup', function (e) {
		 	$("#alertModal").find('.main-area').hide();
		 	$("#alertModal").find('.loader').removeClass('hide');
		 	console.log('Removing id:' + rcmdOb.rcmdId, rcmdOb.itemId + ', name: ' + rcmdOb.name);
		});
		$('#recommendModal').off('hidden.bs.modal');	
	})
}

function getMyRecommendations(restId) {
	accessToken = $('#accessToken').val();
	var dataOb = {
			merchantId : restId,
			page : 1
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/recommendation/getMyRecommendations",
    	  contentType : "application/json",
    	  //beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var myRecommendationsHtml = '';
    		  if (response.result) {
    			  if (response.recommendations.length > 0) {
    				  for (var i=0; i<response.recommendations.length; i++) {
    					  myRecommendationsHtml += 
    						  '<div class="row">'+
		                          '<div class="col-xs-12 recommended-item cursor-pointer">'+
		                              '<div class="bold recommend-item-name">'+
		                                  '<span class="hide recommendation-id">'+response.recommendations[i].id+'</span>'+
		                              	  '<span class="hide item-id">'+response.recommendations[i].itemId+'</span>'+
		                                  '<span class="bna-color item-hash">#'+(i+1)+'</span>'+
		                                  '&nbsp; <span class="item-name">'+response.recommendations[i].name+'</span>'+
		                              '</div>'+
		                              '<div class="recommend-item-desc">'+
		                              	(response.recommendations[i].description!=null?response.recommendations[i].description:'')+
		                              '</div>'+                                                                         
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

function activateUpdateRcmdModal() {
	$(".recommended-item").on('mouseup', function(e){
    	rcmdOb = {
    		rcmdId: $(this).find('.recommendation-id').html(),
    		itemId: $(this).find('.item-id').html(),
    		name: $(this).find('.item-name').html(),
    		desc: $(this).find('.recommend-item-desc').html().trim()
    	};
    	openRecommendationModal(rcmdOb.rcmdId, rcmdOb.itemId, rcmdOb.name, rcmdOb.desc, true);
    });
}