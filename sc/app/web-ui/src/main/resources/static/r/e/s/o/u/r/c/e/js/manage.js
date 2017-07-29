var timeout;
var suggestions = [];
var cuisines = [];
$(document).ready(function() {
	$("#rcmd-recommendations").val(0);
    $('#restaurantName').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		merchantSource(query, process);
        }
    });
    
    $('#rcmd-restaurantName').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		merchantSource(query, process);
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
        },
        updater:function (item) {
        	var sName = $("#suggestion-name-display").html();
        	if (sName == '') {
        		sName = item.name;
        	} else {
        		sName = sName + ', ' + item.name;	
        	}
        	suggestions.push(item.id);      	
        	$("#suggestion-name-display").html(sName);
	        return item;
	    },
	    afterSelect: function(item) {
	    	$("#suggestionName").val('');
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
        },
        updater:function (item) {
        	var cName = $("#cuisine-name-display").html();
        	if (cName == '') {
        		cName = item.name;
        	} else {
        		cName = cName + ', ' + item.name;	
        	}
        	cuisines.push(item.id);      	
        	$("#cuisine-name-display").html(cName);
	        return item;
	    },
	    afterSelect: function(item) {
	    	$("#cuisineName").val('');
	    }
    });

    $('#itemName').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		itemSource(query, process, $('#restaurantName'));
	    }
    });
    
    $('#rcmd-itemName').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		itemSource(query, process, $('#rcmd-restaurantName'));
	    }
    });
    
    $("#imageSearch-add").typeahead({
    	minLength: 2,
        autoSelect: true,
        source: function(query, process) {
          imageSource(query, process);
        },
        updater:function (item) {
            $("#image").val(item.url);
            $("#thumbnail").val(item.thumbnail);
            return item;
        }
    });
});

function addItem() {
	var merchant = $('#restaurantName');
	var currentMerchant = merchant.typeahead("getActive");
	var $merchantName = merchant.val();
	var $merchantId = '';
	var $itemName = '';
	var $imageUrl = $("#image").val().trim();
	var $thumbnail = $("#thumbnail").val().trim();
	//var $isActive = ($("#isActive:checked").val() == 'true') ? true : false;
	var $isActive = true;

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
			if ($imageUrl == '') {
				alertMessage('Please enter item image url');
				return;
			}

			if ($thumbnail == '') {
				alertMessage('Please enter item thumbnail url');
				return;
			}
			if (!currentItem || (currentItem && currentItem.name != $itemName)) {
				var input = confirm('Confirm Action \nName                              : '+$itemName+'\nRestaurant                      : '+$merchantName+'\nSuggestions                : '+suggestions+'\nCuisines                      : '+cuisines+'\nImage Url                         : '+$imageUrl+'\nThumbnail                        : '+$thumbnail+'\nIs Active                           : '+$isActive);
				if (input == true) {
					var dataOb = {
							name : $itemName,
							merchantId : $merchantId,
							cuisineIds : cuisines,
							suggestionIds : suggestions,
							imageUrl : $imageUrl,
							thumbnail : $thumbnail,
							isActive : $isActive
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
							  // reset dish name
			      			  $("#itemName").val('');
							  
			      			  //reset cuisine data						  
							  cuisines = [];
							  $("#cuisineName").val('');
							  $("#cuisine-name-display").html('');

							  //reset suggestion data
							  suggestions = [];
							  $("#suggestionName").val('');
							  $("#suggestion-name-display").html('');
							  
							  
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

function addRecommendations() {
	var merchant = $('#rcmd-restaurantName');
	var currentMerchant = merchant.typeahead("getActive");
	var $merchantName = merchant.val();
	var item = $('#rcmd-itemName');
	var currentItem = item.typeahead("getActive");
	var $itemName = item.val();
	var $rating = $("#rcmd-rating").val();
	var $recommendations = $("#rcmd-recommendations").val();

	if (currentMerchant) {
		if (currentMerchant.name == $merchantName) {
			if (currentItem) {
				if (currentItem.name == $itemName) {
					if ($recommendations == '' || $recommendations <= 0) {
						alertMessage('Recommendations count should be more than 0');
						return;
					}
					
					if ($rating == '' || $rating <= 0 || $rating > 5) {
						alertMessage('Recommendations count should be between 0 and 5');
						return;
					}

					var dataOb = {
			    			itemId : currentItem.id,
			    			rating : $rating,
			    			rcmdCount : $recommendations
			    	};
			        return $.ajax({
			      	  method: "POST",
			      	  url: "/socyal/management/addRecommendations",
			      	  contentType : "application/json",
			      	  data: JSON.stringify(dataOb)
			      	})
			      	  .done(function(response) {
			      		  if (response.result) {
			      			  alertMessage('Successfully added recommendations for item : ' + $itemName);
			      			  $("#rcmd-recommendations").val(0);
			      		  } else {
			    			  handleErrorCallback(response);
			    		  }	          		  
			      	  });
				} else {
					alert('Please select an item to update');	
				}				
			} else {
				alert('Please select an item to update');	
			}
		} else {
			alert('Please select a restaurant');
		}	
	} else {
		alert('Please select a restaurant');
	}
	return;
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

function imageSource(query, process) {
  if (timeout) {
        clearTimeout(timeout);
    }
    timeout = setTimeout(function() {
      var dataOb = {
          searchString : query
      };
        return $.ajax({
          method: "POST",
          url: "/socyal/management/getItemImages",
          contentType : "application/json",
          data: JSON.stringify(dataOb)
        })
          .done(function(response) {
            if (response.result) {
              return process(response.images);                   
            } else {
            handleErrorCallback(response);
          }                 
          });
    }, 500);
}

function merchantSource(query, process) {
  if (timeout) {
	  clearTimeout(timeout);
  }
  timeout = setTimeout(function() {
  	var dataOb = {
  			searchString : query
  	};
      return $.ajax({
    	  method: "POST",
    	  url: "/socyal/management/searchMerchant",
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

function itemSource(query, process, merchant) {
	if (timeout) {
        clearTimeout(timeout);
    }
	timeout = setTimeout(function() {
    	//var merchant = $('#restaurantName');
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

function resetCuisines() {
	cuisines = [];
	$("#cuisineName").val('');
	$("#cuisine-name-display").html('');
}

function resetSuggestions() {
	suggestions = [];
	$("#suggestionName").val('');
	$("#suggestion-name-display").html('');
}

function alertMessage(message) {
	alert(message);
}