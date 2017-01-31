$(document).ready(function() {
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