$(document).ready(function() {
    var words = [
		{text: "bananaa", weight: 13, color: "#8d4ae2", html: {class: "wc-custom-class-1"}},
		{text: "love", weight: 9.3, color: "#fbdb81", html: {class: "wc-custom-class-2"}},
		{text: "checkin", weight: 12, color: "#b4b7b9", html: {class: "wc-custom-class-3"}},
		{text: "discover", weight: 12, color: "#fbdb81", html: {class: "wc-custom-class-3"}},
		{text: "follow", weight: 10.4, color: "#d7d8d9", html: {class: "wc-custom-class-2"}},
		{text: "fun", weight: 10, color: "#212e3b", html: {class: "wc-custom-class-3"}},
		{text: "rewards", weight: 11, color: "#fbdb81", html: {class: "wc-custom-class-2"}},
		{text: "excitement", weight: 9.2, color: "#fbdb81", html: {class: "wc-custom-class-3"}},
		{text: "friends", weight: 11, color: "#d7d8d9", html: {class: "wc-custom-class-2"}},
		{text: "ratings", weight: 12, color: "#d6c4f4", html: {class: "wc-custom-class-2"}},
		{text: "hangout", weight: 11, color: "#d6c4f4", html: {class: "wc-custom-class-3"}},
		{text: "like", weight: 10, color: "#d7d8d9", html: {class: "wc-custom-class-3"}},
		{text: "share", weight: 10.5, color: "#d6c4f4", html: {class: "wc-custom-class-2"}},
		{text: "tag", weight: 9.6, color: "#8d4ae2", html: {class: "wc-custom-class-3"}},
		{text: "joy", weight: 9.6, color: "#212e3b", html: {class: "wc-custom-class-3"}},
		{text: "being social", weight: 10, color: "#d7d8d9", html: {class: "wc-custom-class-2"}},
		{text: "feedbacks", weight: 9.8, color: "#8d4ae2", html: {class: "wc-custom-class-3"}},
		{text: "food", weight: 12, color: "#212e3b", html: {class: "wc-custom-class-3"}},
		{text: "happiness", weight: 8, color: "#fbdb81", html: {class: "wc-custom-class-2"}}
	];

    $('.word-cloud').jQCloud(words, {
		height: 500,
		autoResize: true,
		delayedMode : true
	});
    
    var timerid;    
    $("#search-field").on("input",function(e){
        var value = $(this).val();
        if($(this).data("lastval")!= value){

            $(this).data("lastval",value);        
            clearTimeout(timerid);

            timerid = setTimeout(function() {
                //change action
                $('#excitementModal').modal();
            },500);
        };
    });

    $('#playStoreLink').click(function() {
    	$('#appLaunchModal').modal();
    });
    
    $('#appStoreLink').click(function() {
    	$('#appLaunchModal').modal();
    });
    
});