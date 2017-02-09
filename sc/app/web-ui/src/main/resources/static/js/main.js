$(document).ready(function() {
    var timerid;
    var accessToken = $('#accessToken').val();
    $("#search-field").on("input",function(e){
        var value = $(this).val();
        if($(this).data("lastval")!= value){

            $(this).data("lastval",value);        
            clearTimeout(timerid);

            timerid = setTimeout(function() {
                //change action
            	var dataOb = {
            			searchString : $("#search-field").val()
            	};
                $.ajax({
                	  method: "POST",
                	  url: "/socyal/merchant/searchMerchant",
                	  contentType : "application/json",
                	  beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
                	  data: JSON.stringify(dataOb)
                	})
                	  .done(function(response) {
                		  var sugestionHtml = '';
                		  for (var i=0; i < response.merchants.length; i++) {
                			  sugestionHtml += 	'<div class="col-xs-12 suggestion-wrapper">'+
					                			'<a href="'+ $('#city').val() + "/" +response.merchants[i].id +'" >'+
					                			'<p class="suggestion" align="left">';
                			  sugestionHtml += '<b>' + response.merchants[i].name + "</b>, " + response.merchants[i].shortAddress;
                			  if ( i == (response.merchants.length*1)-1) {
                				  sugestionHtml += '</p></a></div>';
                			  } else {
                				  sugestionHtml += '</p></a><hr /></div>';
                			  }
                		  }
                		  $('.search-box-suggestion').html(sugestionHtml);
                	  });
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