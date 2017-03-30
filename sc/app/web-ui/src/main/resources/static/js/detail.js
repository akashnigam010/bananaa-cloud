$(document).ready(function() {
    var primaryImage = $("#primaryImageTemp").html();
    var background = "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0)),url("+primaryImage+")";
    $(".primary-image-banner").css("background", background);
    $(".primary-image-banner").css("background-repeat", "no-repeat");
    $(".primary-image-banner").css("background-position", "center");
    $(".primary-image-banner").css("background-size", "cover");

        $('#typeahead-item-name').typeahead({
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
        	openRecommendationModal('', '', false);
        });
        
        getMyRecommendations(1);
});

function openRecommendationModal(name, desc, isUpdateFlag) {
	$("#typeahead-item-name").val(name);
	$("#recommend-desc-area").val(desc);
	if (isUpdateFlag) {
		$("#removeRecommendation").removeClass('hide');
		$("#recommendSubmit").html('Update recommendation');
	} else {
		$("#removeRecommendation").addClass('hide');
		$("#recommendSubmit").html('Add recommendation');
	}
	$('#recommendModal').modal('show');
}

function submitRecommendation() {
	var $input = $('#typeahead-item-name');
	var current = $input.typeahead("getActive");
	if (current) {
	    // Some item from your model is active!
	    if (current.name == $input.val()) {
	      // This means the exact match is found. Use toLowerCase() if you want case insensitive match.
	      console.log('exact match');
	      console.log(current);		  
	    } else {
	      //console.log('partial match');
	    }
	  } else {
	    //console.log('no match');
	  }
}

function removeRecommendation() {
	$('#recommendModal').modal('hide');
	$("#alertText").html('Are you sure you want to remove ' + rcmdOb.name + ' from your recommendations ?')
	$("#alertHeading").html('Confirm action')
	$("#alertModal").modal('show');
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
		                                  '<span class="hide item-id">'+response.recommendations[i].id+'</span>'+
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
    				  $('.my-recommendation-loader').hide();
    				  activateUpdateRcmdModal();
    			  }
    		  }
    	  });
}

function activateUpdateRcmdModal() {
	$(".recommended-item").on('mouseup', function(e){
    	var id = e.currentTarget.childNodes[0].childNodes[0].childNodes[0].data;
    	var hash = e.currentTarget.childNodes[0].childNodes[1].childNodes[0].data;
    	var name = e.currentTarget.childNodes[0].childNodes[3].childNodes[0].data;
    	var desc = '';
    	if (e.currentTarget.childNodes[1].childNodes.length != 0) {
    		desc = e.currentTarget.childNodes[1].childNodes[0].data;
    	} 
    	rcmdOb = {
    		id: id,
    		name: name
    	};
    	openRecommendationModal(name, desc.trim(), true);
    });
}