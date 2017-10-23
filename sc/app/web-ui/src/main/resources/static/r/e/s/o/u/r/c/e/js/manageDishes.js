var data = {};
var dishes = {};
var editDish = {};
var editDishCopy = {};
var selectRestaurantId = '';

$(document).ready(function() {
	$('#restaurantNameModal').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		merchantSource(query, process);
        },
        updater:function (restaurant) {
        	selectRestaurantId = restaurant.id;
        	loadDishes(restaurant.id);
	    	return restaurant;
	    }
    });
	
	
	$('#suggestionNameModal').typeahead({
		minLength: 2,
		source: function(query, process) {
    		suggestionSource(query, process);
        },
	    updater:function (item) {
	    	var sName = $("#suggestion-name-display-modal").html();
	    	if (sName == '') {
	    		sName = item.name;
	    	} else {
	    		sName = sName + ', ' + item.name;	
	    	}
	    	editDishCopy.suggestions.push({
	    		id: item.id,
	    		name: item.name
	    	});      	
	    	editDishCopy.suggestionsHtml = sName;
	    	$("#suggestion-name-display-modal").html(sName);
	    	return item;
	    },
	    afterSelect: function(item) {
	    	$("#suggestionNameModal").val('');
	    }
	});
	
	$('#cuisineNameModal').typeahead({
		minLength: 2,
		source: function(query, process) {
    		cuisineSource(query, process);
        },
	    updater:function (item) {
	    	var cName = $("#cuisine-name-display-modal").html();
	    	if (cName == '') {
	    		cName = item.name;
	    	} else {
	    		cName = cName + ', ' + item.name;	
	    	}
	    	editDishCopy.cuisines.push({
	    		id: item.id,
	    		name: item.name
	    	});
	    	editDishCopy.cuisinesHtml = cName;     	
	    	$("#cuisine-name-display-modal").html(cName);
	        return item;
	    },
	    afterSelect: function(item) {
	    	$("#cuisineNameModal").val('');
	    }
	});
});

function loadDishes(merchantId) {
	$(".grid-body").html('');
	$(".loader-animation").removeClass('hide');
	// make the service call with merchant id and load all dishes to grid
	var dataOb = {
		  id : merchantId
	};
	$.ajax({
		  method: "POST",
		  url: "/socyal/management/getAllItems",
		  contentType : "application/json",
		  data: JSON.stringify(dataOb)
  	})
  	.done(function(response) {
  		$(".loader-animation").addClass('hide');
		  if (response.result) {
			  data = response.dishes;
			  dishes = data;
			  var gridBodyHtml = '';
			  for (id in dishes) {
				gridBodyHtml += '<tr class="cursor-pointer" id="'+id+'">'+
								'<td onclick="editRow(this);">'+id+'</td>'+
								'<td onclick="editRow(this);">'+dishes[id].name+'</td>'+
								'<td>'+getVegnonveg(dishes[id].vegnonveg, id)+'</td>'+
								'<td onclick="editRow(this);">'+getTags(dishes[id].suggestions)+'</td>'+
								'<td onclick="editRow(this);">'+getTags(dishes[id].cuisines)+'</td>'+
								'<td><button class="bna-button-light font-1-3" style="margin:0; padding: 0; background-color: transparent;"'+
								' onclick="deleteItem('+id+');">&#10006;</button></td>'+
								'</tr>';
			  }
			  $(".grid-body").html(gridBodyHtml);
		  } else {
			  handleErrorCallback(response);
		  }
	  });
}

function getVegnonveg(vegnonveg, id) {
	var radio1 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="1"></input>';
	var radio2 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="2"></input>';
	var radio3 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="3"></input>';
	if (vegnonveg == 1) {
		radio1 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="1" checked="checked"></input>';
	} else if (vegnonveg == 2) {
		radio2 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="2" checked="checked"></input>';
	} else if (vegnonveg == 3) {
		radio3 = '<input class="no-margin" type="radio" name="vegOrNonVeg'+id+'" value="3" checked="checked"></input>';
	}
	
	return '<div>'+
              '<div class="radio-inline">'+
                radio1+
              '</div>'+
              '<div class="radio-inline">'+
                radio2+
              '</div>'+
              '<div class="radio-inline">'+
                radio3+
              '</div>'+
            '</div>';
}

function saveVegNonvegValues() {
	var input = confirm("Are you sure you want to bulk update ?? This is a costly process.");
	if(input) {
		var updateArr = [];
		var radioVal;
		for (id in dishes) {
			radioVal = $('input[name=vegOrNonVeg'+id+']:checked').val();
			if (radioVal != undefined) {
				updateArr.push({
					id : id,
					value : radioVal
				})
			}	
		}
		var dataOb = {
    			values : updateArr
    	};
		$(".loader-animation").removeClass('hide');
		$.ajax({
			  method: "POST",
			  url: "/socyal/management/updateVegNonvegValues",
			  contentType : "application/json",
			  data: JSON.stringify(dataOb)
	  	})
	  	.done(function(response) {
	  		$(".loader-animation").addClass('hide');
			  if (response.result) {
				  alert('Saved successfully');
			  } else {
				  handleErrorCallback(response);
			  }
		  });
	}
	return;
}

function getTags(tags) {
	var tagHtml = '';
	for (var i=0; i<tags.length; i++) {
		if (i == tags.length-1) {
			tagHtml += tags[i].name;
		} else {
			tagHtml += tags[i].name + ', ';
		}		
	}
	return tagHtml;
}

function editRow(child) {
	var row = $(child).closest('tr').attr('id');
	editDish = dishes[row];
	// create deep copy of dish being edited
	editDishCopy = jQuery.extend(true, {}, editDish);
	editDishCopy.suggestionsHtml = getTags(editDishCopy.suggestions);
	editDishCopy.cuisinesHtml = getTags(editDishCopy.cuisines);
	setModalValues(editDishCopy);
	$('#editModal').modal('show');
}

function setModalValues(dish) {
	$("#itemNameModal").val(dish.name);
	var radioVal = dish.vegnonveg;
	if (radioVal == 1 || radioVal == 2 || radioVal == 3) {
		$("input:radio[name=vegOrNonVegModal][value=" + radioVal + "]").prop('checked', true);
	} else {
		$('input[name="vegOrNonVegModal"]').prop('checked', false);
	}	
	$("#suggestion-name-display-modal").html(dish.suggestionsHtml);
	$("#cuisine-name-display-modal").html(dish.cuisinesHtml);
	$("#imageModal").val(dish.image);
	$("#thumbnailModal").val(dish.thumbnail);
}

function resetModalSuggestions() {
	editDishCopy.suggestions = [];
	editDishCopy.suggestionsHtml = '';
	$("#suggestion-name-display-modal").html(editDishCopy.suggestionsHtml);
}

function resetModalCuisines() {
	editDishCopy.cuisines = [];
	editDishCopy.cuisinesHtml = '';
	$("#cuisine-name-display-modal").html(editDishCopy.cuisinesHtml);
}

function deleteItem(id) {
	var input = confirm('Are you sure to delete Item Id : ' + id);
	if (input == true) {
		var dataOb = {
				  id : id
			};
			$.ajax({
				  method: "POST",
				  url: "/socyal/management/deleteItem",
				  contentType : "application/json",
				  data: JSON.stringify(dataOb)
		  	})
		  	.done(function(response) {
	    		  if (response.result) {
	    			  alert('Item successfully deleted, refereshing list');
	    			  if (selectRestaurantId != '') {
	    				  loadDishes(selectRestaurantId);
	    			  } else {
	    				  alert('Please select a restaurant');
	    			  }
	    			  
	    			  $('#editModal').modal('hide');
	    		  } else {
	    			  handleErrorCallback(response);
	    		  }
	    	  });
	} else{ 
		return;
	}
}

function refreshList() {
	if (selectRestaurantId != '') {
		loadDishes(selectRestaurantId);		  
	} else {
		alert('Please select a restaurant');
	}
	return;
}

function updateDish() {
	editDishCopy.name = $("#itemNameModal").val();
	editDishCopy.image = $("#imageModal").val();
	editDishCopy.thumbnail = $("#thumbnailModal").val();
	editDishCopy.vegnonveg = $("input:radio[name ='vegOrNonVegModal']:checked").val();
	if (editDishCopy.vegnonveg === undefined) {
		alert("Please select veg/non veg type");
		return;
	}
	if (isDishEdited()) {
		$(".loader-animation").removeClass('hide');
		//service call to update dish details	
		var dataOb = {
			  id : editDishCopy.id,
			  name : editDishCopy.name,
			  vegnonveg: editDishCopy.vegnonveg,
			  thumbnail: editDishCopy.thumbnail,
			  imageUrl: editDishCopy.image,
			  isActive: true,
			  cuisines: editDishCopy.cuisines,
			  suggestions: editDishCopy.suggestions
		};
		$.ajax({
			  method: "POST",
			  url: "/socyal/management/updateItem",
			  contentType : "application/json",
			  data: JSON.stringify(dataOb)
	  	})
	  	.done(function(response) {
	  		$(".loader-animation").addClass('hide');
    		  if (response.result) {
    			//update row with new details
				var newDishTdHtml = 	'<td onclick="editRow(this);">'+editDishCopy.id+'</td>'+
									'<td onclick="editRow(this);">'+editDishCopy.name+'</td>'+
									'<td onclick="editRow(this);">'+getVegnonveg(editDishCopy.vegnonveg)+'</td>'+
									'<td onclick="editRow(this);">'+getTags(editDishCopy.suggestions)+'</td>'+
									'<td onclick="editRow(this);">'+getTags(editDishCopy.cuisines)+'</td>'+
									'<td><button class="bna-button-light font-1-3" style="margin:0; padding: 0; background-color: transparent;"'+
									' onclick="deleteItem('+editDishCopy.id+');">&#10006;</button></td>';
				
				$('#'+editDishCopy.id+'').html(newDishTdHtml);
				//update original data structure with new details
				data[editDishCopy.id] = editDishCopy;
				$('#editModal').modal('hide');
    		  } else {
    			  $('#editModal').modal('hide');
    			  handleErrorCallback(response);
    		  }
    	  });
	} else {
		$('#editModal').modal('hide');
	}
}

function isDishEdited() {
	if (editDishCopy.name != editDish.name || 
		isTagEdited(editDishCopy.suggestions, editDish.suggestions) || 
		isTagEdited(editDishCopy.cuisines, editDish.cuisines) || 
		editDishCopy.thumbnail != editDish.thumbnail || 
		editDishCopy.image != editDish.image ||
		editDishCopy.vegnonveg != editDish.vegnonveg) {
		return true;
	} else {
		return false;
	}
}

function isTagEdited(tagsCopy, tags) {
	if (tagsCopy.length != tags.length) {
		return true;
	} else {
		var tagIds = [];
		var tagCopyIds = [];
		for (var i=0; i<tagsCopy.length; i++) {
			tagIds[i] = tags[i].id;
			tagCopyIds[i] = tagsCopy[i].id;
		}
		if(_.isEqual(_.sortBy(tagIds), _.sortBy(tagCopyIds))) {
			return false;
		} else{
			return true;
		}
	}	
}