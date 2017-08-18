var data = {};
var dishes = {};
var editDish = {};
var editDishCopy = {};

$(document).ready(function() {
	$('#restaurantNameModal').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		merchantSource(query, process);
        },
        updater:function (restaurant) {
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
				gridBodyHtml += '<tr class="cursor-pointer" id="'+id+'" onclick="editRow(this.id);">'+
								'<td>'+id+'</td>'+
								'<td>'+dishes[id].name+'</td>'+
								'<td>'+getTags(dishes[id].suggestions)+'</td>'+
								'<td>'+getTags(dishes[id].cuisines)+'</td>'+
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

function editRow(row) {
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
	    			  alert('Item successfully deleted, please manually refresh the list');
	    			  $('#editModal').modal('hide');
	    		  } else {
	    			  handleErrorCallback(response);
	    		  }
	    	  });
	} else{ 
		return;
	}
}

function updateDish() {
	editDishCopy.name = $("#itemNameModal").val();
	editDishCopy.image = $("#imageModal").val();
	editDishCopy.thumbnail = $("#thumbnailModal").val();
	if (isDishEdited()) {
		$(".loader-animation").removeClass('hide');
		//service call to update dish details	
		var dataOb = {
			  id : editDishCopy.id,
			  name : editDishCopy.name,
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
				var newDishTdHtml = 	'<td>'+editDishCopy.id+'</td>'+
									'<td>'+editDishCopy.name+'</td>'+
									'<td>'+getTags(editDishCopy.suggestions)+'</td>'+
									'<td>'+getTags(editDishCopy.cuisines)+'</td>'+
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
		editDishCopy.image != editDish.image) {
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