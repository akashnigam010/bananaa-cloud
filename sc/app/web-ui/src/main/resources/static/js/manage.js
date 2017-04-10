var timeout;
$(document).ready(function() {
	$("#recommendations").val(0);
    $('#restaurantName').typeahead({
    	minLength: 2,
    	autoSelect: true,
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
              	  url: "/socyal/merchant/searchMerchant",
              	  contentType : "application/json",
              	  data: JSON.stringify(dataOb)
              	})
              	  .done(function(response) {
              		  if (response.result) {
              			  return process(response.merchants);              			
              		  } else {
            			  handleErrorCallback(response);
            		  }	          		  
              	  });
            }, 500);
        }
    });
    
    $('#suggestionNameAdd').typeahead({
    	minLength: 2,
    	source: function(query, process) {
    		suggestionSource(query, process);
        }
    });
    
    $('#suggestionName').typeahead({
    	minLength: 2,
    	source: function(query, process) {
    		suggestionSource(query, process);
        }
    });

    $('#cuisineNameAdd').typeahead({
    	minLength: 2,
    	source: function(query, process) {
    		cuisineSource(query, process);
        }
    });
    
    $('#cuisineName').typeahead({
    	minLength: 2,
    	source: function(query, process) {
    		cuisineSource(query, process);
        }
    });

    $('#itemName').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
            if (timeout) {
                clearTimeout(timeout);
            }
            timeout = setTimeout(function() {
            	var merchant = $('#restaurantName');
            	var $merchantName = merchant.val();
            	var current = merchant.typeahead("getActive");
            	if (current) {
            		if (current.name === $merchantName) {
	            		var dataOb = {
	            			searchString : query,
	            			merchantId : current.id
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
	            	} else {
	            		alertMessage('Please select a restaurant');	
	            	}
            	} else {
            		alertMessage('Please select a restaurant');
            	}          	           	
            }, 500);
        }
    });     
});

function addItem() {
	var merchant = $('#restaurantName');
	var currentMerchant = merchant.typeahead("getActive");
	var $merchantName = merchant.val();
	var $merchantId = '';
	var $itemName = '';
	var $suggestionId = '';
	var $suggestionName = '';
	var $cuisineId = '';
	var $cuisineName = '';
	var $imageUrl = $("#image").val().trim();
	var $recommendations = 0;
	var $isActive = ($("#isActive:checked").val() == 'true') ? true : false;

	if ($("#recommendations").val() == '' || $("#recommendations").val() < 0) {
		alertMessage('Incorrect recommendations count');
		return;
	}	
	$recommendations = $("#recommendations").val();

	var currentCuisine = $('#cuisineName').typeahead("getActive");
	if (currentCuisine != undefined) {
		$cuisineId = currentCuisine.id;
		$cuisineName = currentCuisine.name;
	}

	var currentSuggestion = $('#suggestionName').typeahead("getActive");
	if (currentSuggestion != undefined) {
		$suggestionId = currentSuggestion.id;
		$suggestionName = currentSuggestion.name;
	}

	if (currentMerchant) {
		if (currentMerchant.name == $merchantName) {
			$merchantId = currentMerchant.id;
			var itemName = $('#itemName');
			$itemName = itemName.val();
			var currentItem = itemName.typeahead("getActive");
			if ($itemName === '') {
				alertMessage('Enter an item name');
				return;
			}		
			if (!currentItem || (currentItem && currentItem.name != $itemName)) {
				var input = confirm('Confirm Action \nName                              : '+$itemName+'\nRestaurant                      : '+$merchantName+'\nSuggestion Name            : '+$suggestionName+'\nCuisine Name                  : '+$cuisineName+'\nRecommendation Count : '+$recommendations+'\nImage Url                         : '+$imageUrl+'\nIs Active                           : '+$isActive);
				if (input == true) {
					var dataOb = {
							name : $itemName,
							merchantId : $merchantId,
							cuisineId : $cuisineId,
							suggestionId : $suggestionId,
							imageUrl : $imageUrl,
							isActive : $isActive,
							recommendations : $recommendations
			    	};
			        return $.ajax({
			      	  method: "POST",
			      	  url: "/socyal/management/addItem",
			      	  contentType : "application/json",
			      	  data: JSON.stringify(dataOb)
			      	})
			      	  .done(function(response) {
			      		  if (response.result) {
			      			  alertMessage($itemName + ' successfully added');
			      			  $("#itemName").val('');
			      			  $("#recommendations").val(0);
			      			  $("#cuisineName").val('');
			      			  $("#suggestionName").val('');
			      			  $("#image").val('');
			      		  } else {
			    			  handleErrorCallback(response);
			    		  }	          		  
			      	  });
				} else {
					return;
				}
			} else {
				alertMessage('Item already added');
			}
		} else {
			alertMessage('Restaurant not selected');
		}		
	} else {
		alertMessage('Restaurant not selected');
	}
}

function addCuisine() {
	var cuisine = $('#cuisineNameAdd');
	var $cuisineName = cuisine.val();
	var current = cuisine.typeahead("getActive");
	if ($cuisineName === '') {
		alertMessage('Enter a cuisine name');
		return;
	}	

	if (!current || (current && current.name != $cuisineName)) {
		var dataOb = {
    			name : $cuisineName
    	};
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/management/addCuisine",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			  alertMessage($cuisineName + ' successfully added');              			
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
	} else {
		alertMessage('Cuisine already exists');
	}
}

function addSuggestion() {
	var suggestion = $('#suggestionNameAdd');
	var $suggestionName = suggestion.val();
	var current = suggestion.typeahead("getActive");
	if ($suggestionName === '') {
		alertMessage('Enter a suggestion name');
		return;
	}	

	if (!current || (current && current.name != $suggestionName)) {
		var dataOb = {
    			name : $suggestionName
    	};
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/management/addSuggestion",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			  alertMessage($suggestionName + ' successfully added');              			
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
	} else {
		alertMessage('Suggestion name already exists');
	}
}

function cuisineSource(query, process) {
	if (timeout) {
        clearTimeout(timeout);
    }
    timeout = setTimeout(function() {
    	var dataOb = {
    			searchString : query
    	};
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/management/getCuisines",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			  return process(response.cuisines);              			
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
    }, 500);
}

function suggestionSource(query, process) {
	if (timeout) {
        clearTimeout(timeout);
    }
    timeout = setTimeout(function() {
    	var dataOb = {
    			searchString : query
    	};
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/management/getSuggestions",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		  if (response.result) {
      			  return process(response.suggestions);              			
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
    }, 500);
}

function alertMessage(message) {
	alert(message);
}