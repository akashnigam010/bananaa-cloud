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

function next() {
    var page = getUrlVars('page');
    if (page == null) {
        page = 1;
    }
    window.location = updateUrlParameter(window.location.href, 'page', ++page);
};

function prev() {
    var page = getUrlVars('page');
    if (page == null || page == 1) {
        return;
    }
    window.location = updateUrlParameter(window.location.href, 'page', --page);
};

/* Reads query strings from the url */
function getUrlVars(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

// Add / Update a key-value pair in the URL query parameters
function updateUrlParameter(uri, key, value) {
    // remove the hash part before operating on the uri
    var i = uri.indexOf('#');
    var hash = i === -1 ? ''  : uri.substr(i);
         uri = i === -1 ? uri : uri.substr(0, i);

    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
        uri = uri.replace(re, '$1' + key + "=" + value + '$2');
    } else {
        uri = uri + separator + key + "=" + value;
    }
    return uri + hash;  // finally append the hash as well
}