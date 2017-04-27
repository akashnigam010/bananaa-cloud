function submitMessage() {
    $name = $("#name").val();
    $phone = $("#phone").val();
    $email = $("#email").val();
    $message = $("#message").val();

    if ($name == '') {
        $(".error-label-name").removeClass('hide');
        return;
    } else {
        $(".error-label-name").addClass('hide');
    }

    if ($email == '') {
        $(".error-label-email").removeClass('hide');
        return;
    } else {
        $(".error-label-email").addClass('hide');
    }

    if ($message == '') {
        $(".error-label-message").removeClass('hide');
        return;
    } else {
        $(".error-label-message").addClass('hide');
    }
    
    $("#loadingModal").modal('show');

    var dataOb = {
        name : $name,
        phone : $phone,
        email : $email,
        message : $message
    };
    $.ajax({
          method: "POST",
          url: "/socyal/management/sendMessage",
          contentType : "application/json",
          data: JSON.stringify(dataOb)
        })
          .done(function(response) {
        	  $("#loadingModal").modal('hide');
              if (response.result) {
            	  $("#name").val('');
            	  $("#phone").val('');
            	  $("#email").val('');
            	  $("#message").val('');
            	  $(".success-label-message").removeClass('hide');
              } else {
                  handleErrorCallback(response);
              }                       
          });
    
}