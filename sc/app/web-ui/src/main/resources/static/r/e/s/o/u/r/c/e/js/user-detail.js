$(document).ready(function() {
	page = 'user-detail';
	$('#topSearchInput').typeahead(searchConfig($('#topSearchInput')));
	$('#modalSearchInput').typeahead(searchConfig($('#modalSearchInput')));
});