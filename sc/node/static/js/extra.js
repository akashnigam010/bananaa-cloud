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
              if (response.result) {
                  alert('Thanks for your message. We will get back to you shortly.')
              } else {
                  handleErrorCallback(response);
              }                       
          });
    
}