var oneRestaurantToRate = {};
var restaurantToRateIds = [];
$(document).ready(function() {
	$('#restaurantNameRating').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	source: function(query, process) {
    		merchantSource(query, process);
        },
        updater:function (restaurant) {
        	$("#ratingRestId").html(restaurant.id);
        	$("#ratingRestName").html(restaurant.name);
        	$(".rateRest").removeClass('hide');
        	oneRestaurantToRate = {
        		id: restaurant.id,
        		name: restaurant.name
        	};
	    	return restaurant;
	    }
    });
});

function rateARestaurants() {
	if (oneRestaurantToRate.id === undefined) {
		alert('Please select a restaurant to rate');
		return;
	}
	var input = confirm('Run rating engine for : ' + oneRestaurantToRate.name);
	if (input) {
		$(".loader-animation-rating").removeClass('hide');
		var dataOb = {
			  id : oneRestaurantToRate.id
		};
		$.ajax({
			  method: "POST",
			  url: "/socyal/management/runRatingEngineForMerchant",
			  contentType : "application/json",
			  data: JSON.stringify(dataOb)
		})
		.done(function(response) {
			$(".loader-animation-rating").addClass('hide');
			  if (response.result) {
				  alert('Rating successfully completed');
			  } else {
				  handleErrorCallback(response);
			  }
		  });			
	} else {
		return;
	}
}

function rateManyRestaurants() {
	var idTxt = $("#restIdsRating").val().trim().replace(/\s/g,'');
	if (idTxt === '') {
		alert('Please enter comma seperated restaurant ids');
		return;
	}
	restaurantToRateIds = _.uniq(idTxt.split(','));
	var input = confirm('Run rating for Ids ' + restaurantToRateIds);
	if (input) {
		$(".loader-animation-rating").removeClass('hide');
		var dataOb = {
			  ids : restaurantToRateIds
		};
		$.ajax({
			  method: "POST",
			  url: "/socyal/management/runRatingEngine",
			  contentType : "application/json",
			  data: JSON.stringify(dataOb)
		})
		.done(function(response) {
			$(".loader-animation-rating").addClass('hide');
			  if (response.result) {
				  alert('Rating successfully completed');
			  } else {
				  handleErrorCallback(response);
			  }
		  });
	} else {
		return;
	}
}