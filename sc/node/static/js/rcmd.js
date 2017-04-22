$(document).ready(function() {
    var timeout;
    $('#modal-item-name').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
            if (timeout) {
                clearTimeout(timeout);
            }
            timeout = setTimeout(function() {
            	var dataOb = {
            			searchString : query,
            			merchantId : merchantId
            	};
                return $.ajax({
              	  method: "POST",
              	  url: "/socyal/item/searchItems",
              	  contentType : "application/json",
              	  data: JSON.stringify(dataOb)
              	})
              	  .done(function(response) {
              		  if (response.result) {
              			  if (response.items.length == 0) {
              				$('#recommendModal').find('.error-label-name').html('No such item found. Please try again');
              				$('#recommendModal').find('.error-label-name').removeClass('hide');
              			  } else {
              				$('#recommendModal').find('.error-label-name').addClass('hide');
              				return process(response.items);
              			  }              			
              		  } else {
            			  handleErrorCallback(response);
            		  }	          		  
              	  });
            }, 500);
        }
    });
        
    $('#addRecommendButton').on('mouseup', function (e) {
    	if ($("#loginStatus").html() == 'false') {
    		activateLogin();
    		return;
		}
    	openRecommendationModal('', '', '', '', false);
    });
    
    $("#addItemRecommendButton").on('mouseup', function (e) {
    	if ($("#loginStatus").html() == 'false') {
    		activateLogin();
    		return;
		}
    	openItemRecommendationModal(itemId, itemName, '');
    });
    
    $('#modal-item-desc').on('focusin', function(e) {
    	$(this).attr('placeholder', 'Dive down into the specifics! Tell us how this dish or drink stands out to be a recommendation. And remember to keep it short - a minimum of 50 and a maximum of 200 characters. :)');
		$("#modal-item-desc-label").attr('style', 'top:-20px;font-size:10px;color:#9932CC;');
    });
    $('#modal-item-desc').on('focusout', function(e) {
    	$(this).attr('placeholder', '');
    	if ($(this).val() == '') {
    		$("#modal-item-desc-label").attr('style', '');
    	}
    });
});

function openRecommendationModal(rcmdId, itemId, name, desc, isUpdateFlag) {
	$("#recommendModal").find('.main-area').show();
	$("#recommendModal").find('.loader').addClass('hide');
	$("#modal-recommendation-id").val(rcmdId);
	$("#modal-item-id").val(itemId);
	$("#modal-item-name").val(name);
	$("#modal-item-desc").val(desc);
	$("#merchant-name-title").html(' @ '+ merchantName);
	if (desc != '') {
		$("#modal-item-desc-label").attr('style', 'top:-20px;font-size:10px;color:#9932CC;');
	} else {
		$("#modal-item-desc-label").attr('style', '');
	}	
	if (isUpdateFlag) {
		$("#removeRecommendation").removeClass('hide');
		$("#recommendSubmit").html('Update recommendation');
		$("#recommendSubmit").off('mouseup');
		$("#recommendSubmit").on('mouseup', updateRecommendation);
		$('#modal-item-name').prop('disabled', true);
		$("#modal-item-name-label").addClass('hide');
	} else {
		$("#removeRecommendation").addClass('hide');
		$("#recommendSubmit").html('Add recommendation');
		$("#recommendSubmit").off('mouseup');
		$("#recommendSubmit").on('mouseup', addRecommendation);
		$('#modal-item-name').prop('disabled', false);
		$("#modal-item-name-label").removeClass('hide');
	}
	$('#recommendModal').find('.error-label').addClass('hide');
	$('#recommendModal').modal('show');
}

function openItemRecommendationModal(itemId, name, desc) {
	$("#recommendModal").find('.main-area').show();
	$("#recommendModal").find('.loader').addClass('hide');
	$("#modal-item-id").val(itemId);
	$("#modal-item-name").val(name);
	$("#modal-item-desc").val(desc);
	$("#merchant-name-title").html('');
	if (desc != '') {
		$("#modal-item-desc-label").attr('style', 'top:-20px;font-size:10px;color:#9932CC;');
	} else {
		$("#modal-item-desc-label").attr('style', '');
	}
	$("#removeRecommendation").addClass('hide');
	$("#recommendSubmit").html('Add recommendation');
	$("#recommendSubmit").off('mouseup');
	$("#recommendSubmit").on('mouseup', addItemRecommendation);
	$('#modal-item-name').prop('disabled', true);
	$("#modal-item-name-label").addClass('hide');
	
	$('#recommendModal').find('.error-label').addClass('hide');
	$('#recommendModal').modal('show');
}

function updateRecommendation() {
	var $rcmdId = $('#modal-recommendation-id').val();
	var $itemId = $('#modal-item-id').val();
	var $desc = $('#modal-item-desc').val();	
	if ($desc != rcmdOb.desc) {
		if (handleReview($desc)) {
			var dataOb = {
		 			rcmdnId : $rcmdId,
		 			description : $desc
        	};
            return $.ajax({
          	  method: "POST",
          	  url: "/socyal/recommendation/updateRecommendation",
          	  contentType : "application/json",
          	  data: JSON.stringify(dataOb)
          	})
          	  .done(function(response) {
          		$('#recommendModal').modal('hide');
          		  if (response.result) {
          			  if (page == 'detail') {
          				getMyRecommendations()
          			  } else if (page == 'item-detail') {
          				getMyItemRecommendation();
          			  } else {
          				location.reload();
          			  }             			
          		  } else {
        			  handleErrorCallback(response);
        		  }	          		  
          	  });
		}
		return;	
	} else {
		$('#recommendModal').modal('hide');
	}
}

function addRecommendation() {
	var nameInput = $('#modal-item-name');
	var current = nameInput.typeahead("getActive");
	var $rcmdId = $('#modal-recommendation-id').val();
	var $name = nameInput.val();
	var $desc = $('#modal-item-desc').val();
	$('#recommendModal').find('.error-label-name').html('Please select the food or drink item');
	if (current) {
		if (current.name.toLowerCase() == $name.toLowerCase()) {
			$('#recommendModal').find('.error-label-name').addClass('hide');
			if (handleReview($desc)) {
				$("#recommendModal").find('.main-area').hide();
				$("#recommendModal").find('.loader').removeClass('hide');
				var dataOb = {
			 			dishId : current.id,
			 			description : $desc
	        	};
	            return $.ajax({
	          	  method: "POST",
	          	  url: "/socyal/recommendation/addRecommendation",
	          	  contentType : "application/json",
	          	  data: JSON.stringify(dataOb)
	          	})
	          	  .done(function(response) {
	          		$('#recommendModal').modal('hide');
	          		  if (response.result) {
	          			  getMyRecommendations()
	          		  } else {
	        			  handleErrorCallback(response);
	        		  }	          		  
	          	  });
			}	      	
	    } else {
	    	$('#recommendModal').find('.error-label-name').removeClass('hide');
	    }
	  } else {
		  	$('#recommendModal').find('.error-label-name').removeClass('hide');
	  }
}

function addItemRecommendation() {
	var $itemId = $('#modal-item-id').val();
	var $desc = $('#modal-item-desc').val();	
	
	if (handleReview($desc)) {
		var dataOb = {
	 			dishId : $itemId,
	 			description : $desc
    	};
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/recommendation/addRecommendation",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		$('#recommendModal').modal('hide');
      		  if (response.result) {
      			  getMyItemRecommendation()             			
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
	}
	return;
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
		$("#confirmAlertButton").off('mouseup');
		$("#confirmAlertButton").on('mouseup', function (e) {
		 	$("#alertModal").find('.main-area').hide();
		 	$("#alertModal").find('.loader').removeClass('hide');
		 	var dataOb = {
		 			rcmdnId : rcmdOb.rcmdId
        	};
            return $.ajax({
          	  method: "POST",
          	  url: "/socyal/recommendation/removeRecommendation",
          	  contentType : "application/json",
          	  data: JSON.stringify(dataOb)
          	})
          	  .done(function(response) {
          		  $("#alertModal").modal('hide');
          		  if (response.result) {
          			  if (page == 'detail') {
          				getMyRecommendations()
          			  } else if (page == 'item-detail') {
          				getMyItemRecommendation();
          			  } else {
          				location.reload();
          			  }             			
          		  } else {
        			  handleErrorCallback(response);
        		  }	          		  
          	  });
		});
		$('#recommendModal').off('hidden.bs.modal');	
	})
}