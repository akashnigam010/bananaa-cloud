$(document).ready(function() {
	page = 'detail';
    var primaryImage = $("#primaryImageTemp").html();
    if (primaryImage === '') {
    	$(".primary-image-banner").addClass('hide');
    } else {
    	$(".primary-image-banner").removeClass('hide');
    	var background = "url("+primaryImage+")";
        $(".primary-image-banner").css("background", background);
        $(".primary-image-banner").css("background-repeat", "no-repeat");
        $(".primary-image-banner").css("background-position", "center");
        $(".primary-image-banner").css("background-size", "cover");
    }
    
    $('#topSearchInput').typeahead(searchConfig($('#topSearchInput')));
    $('#modalSearchInput').typeahead(searchConfig($('#modalSearchInput')));
});