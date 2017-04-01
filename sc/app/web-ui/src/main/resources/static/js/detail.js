var rcmdOb = {};
$(document).ready(function() {
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");

        $('#modal-item-name').typeahead({
	    	minLength: 2,
	    	autoSelect: true,
	    	//showHintOnFocus: true,
	        source: function (query, process) {
	        	var accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCYW5hbmFhIEFwcGxpY2F0aW9uIiwiYXVkIjoiQmFuYW5hYSBBcHBsaWNhdGlvbiIsImlhdCI6MTQ5MDc5MzQwNCwiZXhwIjoxNTIyMzI5NDA0LCJpbmZvIjp7InJvbGUiOiJHVUVTVCJ9fQ.I6VkrPf3JA5VQxDHSiPal3sFKPrXk8-c4y7SnXdOHxQ";
	        	var dataOb = {
	        			searchString : query
	        	};
	            return $.ajax({
	          	  method: "POST",
	          	  url: "/socyal/merchant/searchMerchant",
	          	  contentType : "application/json",
	          	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
	          	  data: JSON.stringify(dataOb)
	          	})
	          	  .done(function(response) {
	          		  return process(response.merchants);
	          	  });
	        }
	    });
        
        $('#addRecommendButton').on('mouseup', function (e) {
        	openRecommendationModal('', '', '', '', false);
        });
        
        getMyRecommendations(1);
});

function loadPopularDishes() {
	accessToken = $('#accessToken').val();
	var dataOb = {
			id : 1
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/merchant/getPopularItems",
    	  contentType : "application/json",
    	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var popularItemsHtml = '';
    		  if (response.result == true) {
    			  if (response.items.length > 0) {
    				  for (var i=0; i<response.items.length; i++) {
    					  popularItemsHtml += 
    						  '<div class="row">'+
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
			                  '</div>';
    				  }
    				  $('.loadmore-wrapper').html(popularItemsHtml);
    				  $('#loadMoreModal').modal('show');
    			  }
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
	      	$("#recommendModal").find('.main-area').hide();
			$("#recommendModal").find('.loader').removeClass('hide');
	    } else {
	    	handlePartialMatch($rcmdId, $itemId, $name, $desc);
	    }
	  } else {
	  		handlePartialMatch($rcmdId, $itemId, $name, $desc);
	  }
}

function handlePartialMatch(rcmdId, id, name, desc) {
	if (name === rcmdOb.name && desc === rcmdOb.desc) {
		$('#recommendModal').modal('hide');
	}
	if (name === rcmdOb.name) {
		if (desc != rcmdOb.desc) {
			console.log('update this recommendation\'s description  - rcmdId: ' + rcmdId);	
		}
	} else {
		$('#recommendModal').find('.error-label').removeClass('hide');
	}	
}

function removeRecommendation() {
	$("#alertModal").find('.main-area').show();
	$("#alertModal").find('.loader').addClass('hide');
	$('#recommendModal').modal('hide');
	$("#alertText").html('Are you sure you want to remove ' + rcmdOb.name + ' from your recommendations ?')
	$("#alertHeading").html('Confirm action')
	$("#alertModal").modal('show');
	$("#confirmAlertButton").on('mouseup', function (e) {
		$("#alertModal").find('.main-area').hide();
		$("#alertModal").find('.loader').removeClass('hide');
		console.log('Removing id:' + rcmdOb.rcmdId + ', name: ' + rcmdOb.name);
		// $("#alertModal").modal('hide');
	});
}

function getMyRecommendations(restId) {
	accessToken = $('#accessToken').val();
	var dataOb = {
			id : restId
	};
	$.ajax({
    	  method: "POST",
    	  url: "/socyal/merchant/getMyRecommendations",
    	  contentType : "application/json",
    	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
    	  data: JSON.stringify(dataOb)
    	})
    	  .done(function(response) {
    		  var myRecommendationsHtml = '';
    		  if (response.result == true) {
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
    				  if (response.addMore) {
    					  $('.addRecommendButtonDiv').show();
    				  } else {
    					  $('.addRecommendButtonDiv').hide();
    				  }
    				  $('.recommended-wrapper').find('.loader').hide();
    				  activateUpdateRcmdModal();
    			  }
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