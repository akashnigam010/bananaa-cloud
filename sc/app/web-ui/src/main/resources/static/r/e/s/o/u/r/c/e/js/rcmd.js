var rcmdOb = {};
var revItem = {};
var selectedRating = 0;
var flashTime = 50;
var rateVal = new Array();
var star_e = "https://bna-s3.s3.amazonaws.com/img/rate-e.png";
var star_f = "https://bna-s3.s3.amazonaws.com/img/rate-f.png";
rateVal[1] = {
	name: 'EWW',
	display: 'EWW (>.<)',
	color: '#CD1C26'
};
rateVal[2] = {
	name: 'MEH',
	display: 'MEH : /',
	color: '#FF7800'
};
rateVal[3] = {
	name: 'OK OK',
	display: 'OK OK :)',
	color: '#CDD614'
};
rateVal[4] = {
	name: 'YUMMY',
	display: 'YUMMY :D',
	color: '#5BA829'
};
rateVal[5] = {
	name: 'AWESOME',
	display: 'AWESOME !',
	color: '#305D02'
};
$(document).ready(function() {
    var timeout;
    $('#modal-item-name').typeahead({
    	minLength: 2,
    	autoSelect: true,
    	fitToElement: true,
    	matcher: function(item) {
            if (item.name.toLowerCase().includes(this.query.trim().toLowerCase())) {
                return true;
            } else {
                this.query = 'No match found';
                return true;
            }
        },
        displayText: function(item) {
	        return '<div style="padding: 2%;"><span>' + 
	        			item.name + 
	                  '</span> </div>';
		},
		afterSelect: function(item) {
			if (item.id != -999) {
				$('#modal-item-name').val(item.name);
            } else {
            	$('#modal-item-name').val('');
            }
        },
    	source: function(query, process) {
    		var that = this;
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
            }, 300);
        },
        updater:function (item) {
            if (item.id != -999) {
            	tapItemDropdown(item);
            } else {
            	resetModal();
            }
            return item;
        }
    });
        
    $('#addRecommendButton').on('mouseup', function (e) {
    	if ($("#loginStatus").html() == 'false') {
    		activateLogin();
    		return;
		}
    	$(".rating-section").css('pointer-events', 'none');
    	openRecommendationModal();
    });
    
    $(".rate-star").on('click', function() {
    	addRating(getStarId(this.id), true);
    });

    handleReviewCount();
});

function resetModal() {
	$(".rating-section").css('pointer-events', 'none');
	$('#modal-item-name').val('');
	removeStars();
	navigateToRate();
	$(".goto-review").addClass('hide');
}

function tapItemDropdown(item) {
	$(".rating-section").css('pointer-events', '');
	setTimeout(function(){
		$('#modal-item-name').blur();
	}, 100);
    revItem = item;
    if (item.rating == '' || item.rating == null) {
    	activateRatingWidget();
    	$('.reviewTab').find('.recommend-desc-area').val('');
    } else {
    	if (!$(".ratingTab").hasClass('active')) {
			$(".ratingTab").addClass('active');
			$(".reviewTab").removeClass('active');
		}
    	addRating(item.rating, false);
    	$('.reviewTab').find('.recommend-desc-area').val(item.review);
    }
}

function getStarId(sid) {
	if (sid === 's5') return 5;
	if (sid === 's4') return 4;
	if (sid === 's3') return 3;
	if (sid === 's2') return 2;
	if (sid === 's1') return 1;
}

function flashStars() {
	$("#s1").attr("src", star_f);
	setTimeout(function(){
		$("#s2").attr("src", star_f);
		setTimeout(function(){
	        $("#s3").attr("src", star_f);
	        setTimeout(function(){
		        $("#s4").attr("src", star_f);
		        setTimeout(function(){
			        $("#s5").attr("src", star_f);
			        setTimeout(function(){
				        $("#s5").attr("src", star_e);
				        setTimeout(function(){
					        $("#s4").attr("src", star_e);  
					        setTimeout(function(){
						        $("#s3").attr("src", star_e);
						        setTimeout(function(){
									$("#s2").attr("src", star_e);
									setTimeout(function(){
										$("#s1").attr("src", star_e);
								    },flashTime);
							    },flashTime);
						    },flashTime);
					    },flashTime);
				    },flashTime);
			    },flashTime);
		    },flashTime);
	    },flashTime);
    },flashTime);
}

function activateRatingWidget() {
	setTimeout(function() {
		flashStars();
	}, 300);
	$(".rating-section").hover(function(){
    	$(".rate-star").hover(function(){
	    	fillStars(getStarId(this.id));
	    });
    }, function() {
    	removeStars();
    });
	$(".rating-txt").html("ADD YOUR RATING");
	$(".rating-txt").css('color', '');
	$(".rating-txt").addClass('light');
	$(".goto-review").addClass('hide');
}

function navigateToReview() {
	resetHeading('review');
	$(".ratingTab").removeClass('active');
	$(".reviewTab").addClass('active');
	$('.reviewTab').find('.rTitle').html(revItem.name + " @ " + revItem.merchantName);
	$('.reviewTab').find('.rRating').html('"' + revItem.star.name + '"');
	$('.reviewTab').find('.rRating').css('color', revItem.star.color);
	if (revItem.review != '') {
		$('.reviewTab').find('.recommend-desc-area').val(revItem.review);
		var review = revItem.review != null ? revItem.review : '';
		$('.char-count').html(review.length + '/200');
	} else {
		$('.char-count').html('0/200');
	}
	//$('.recommend-desc-area').focus();
}

function navigateToRate() {
	resetHeading('rate');
	$(".ratingTab").addClass('active');
	$(".reviewTab").removeClass('active');
}

function addRating(id, saveRating) {
	$(".rating-section").off("mouseenter mouseleave");
	$(".rate-star").off("mouseenter mouseleave");
	fillStars(id);
	$(".goto-review").removeClass('hide');
	if (selectedRating != id && saveRating) {
		selectedRating = id;
		revItem.rating = id;
		var dataOb = {
			id: revItem.id,
			rating: revItem.rating
		};
		return $.ajax({
      	  method: "POST",
      	  url: "/socyal/recommendation/saveRating",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	.done(function(response) {
      		if (response.result) {
      			$(".rcmd-title").addClass('hide');
            	$('.success-rating').removeClass('hide');
            	setTimeout(function(){
            		$(".rcmd-title").removeClass('hide');
            		$('.success-rating').addClass('hide');
            	}, 2000);
            	if (page == 'detail') {
            		getMyRecommendations();
	  			} else if (page == 'item-detail') {
	  				getMyItemRecommendation();  
	  			}
      		} else {
      			handleErrorCallback(response);
      		}
      	});
	}
}

function resetHeading(to) {
	$('.error-review').addClass('hide');
	$('.success-rating').addClass('hide');
	if (to == 'rate') {
		$(".rcmd-title").html('ADD FOOD RATING');	
	} else {
		$(".rcmd-title").html('ADD FOODVIEW');
	}
	$('.rcmd-title').removeClass('hide');
}

function removeStars() {
	$("#s5, #s4, #s3, #s2, #s1").attr("src", star_e);
	$(".rating-txt").html('add your rating');
	$(".rating-txt").css('color', '#939393');
}

function fillStars(id) {
	if (id == 5) {
		$("#s5, #s4, #s3, #s2, #s1").attr("src", star_f);
	} else if (id == 4) {
		$("#s5").attr("src", star_e);
		$("#s4, #s3, #s2, #s1").attr("src", star_f);
	} else if (id == 3) {
		$("#s5, #s4").attr("src", star_e);
		$("#s3, #s2, #s1").attr("src", star_f);
	} else if (id == 2) {
		$("#s5, #s4, #s3").attr("src", star_e);
		$("#s2, #s1").attr("src", star_f);
	} else if (id == 1) {
		$("#s5, #s4, #s3, #s2").attr("src", star_e);
		$("#s1").attr("src", star_f);
	}
	$(".rating-txt").html(rateVal[id].display);
	$(".rating-txt").css('color', rateVal[id].color);
	revItem.star = rateVal[id];
}

function handleReviewCount() {
	$('.recommend-desc-area').on('keyup',function(){
	    $('.char-count').html($(this).val().length + '/200');
	    revItem.review = $(this).val();
	});
}

function handleReview(desc) {
	if(desc == null || desc.length < 50 || desc.length > 200) {
		$(".rcmd-title").addClass('hide');
    	$('.error-review').removeClass('hide');
    	setTimeout(function(){
    		$(".rcmd-title").removeClass('hide');
    		$('.error-review').addClass('hide');
    	}, 3000);
		return false;
	}
	return true;
}

function openRecommendationModal(rcmdOb) {
	$('#recommendModal').modal('show');
	if (rcmdOb) {
		$("#modal-item-name").val(rcmdOb.name);
		tapItemDropdown(rcmdOb);
	} else {
		resetModal();
		$('#modal-item-name').focus();
	}
}

function addRecommendation() {
	var dataOb = {
		id: revItem.id,
		description: revItem.review
	};
	if (handleReview(dataOb.description)) {
        return $.ajax({
      	  method: "POST",
      	  url: "/socyal/recommendation/saveReview",
      	  contentType : "application/json",
      	  data: JSON.stringify(dataOb)
      	})
      	  .done(function(response) {
      		$('#recommendModal').modal('hide');
      		  if (response.result) {
      			  if (page == 'detail') {
      				getMyRecommendations();
      			  } else if (page == 'item-detail') {
      				getMyItemRecommendation();  
      			  }
      		  } else {
    			  handleErrorCallback(response);
    		  }	          		  
      	  });
	} else {
		return false;
	}
}