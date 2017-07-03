$(document).ready(function() {
	page = 'user-detail';
	$('#topSearchInput').typeahead(searchConfig($('#topSearchInput'), $('#topSearchLocation')));
    $('#modalSearchInput').typeahead(searchConfig($('#modalSearchInput'), $('#modalSearchLocation')));
});