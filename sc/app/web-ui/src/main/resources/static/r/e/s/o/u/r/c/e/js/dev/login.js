var fbClientId = '232637110490690';
var gApiKey = 'AIzaSyCWEn4qyHStxlVn2BF9EO9JfhxMsCRsMR4';
var gClientId = '504868894775-jbme613s3rdb80jf95o44cmpeut11q9c.apps.googleusercontent.com';

window.fbAsyncInit = function() {
	FB.init({
		appId : fbClientId,
		xfbml : true,
		version : 'v2.9',
		status : true
	});
	FB.AppEvents.logPageView();
};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

(function() {
	var po = document.createElement('script');
	po.type = 'text/javascript';
	po.async = true;
	po.src = 'https://apis.google.com/js/client.js?onload=onLoadCallback';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(po, s);
})();

// ********************* facebook login *********************

function fbSignIn() {
	$("#loginModal").find(".loader").removeClass('hide');
	$("#loginModal").find(".modal-body").addClass('hide');
	  FB.getLoginStatus(function(response) {
	    if (response.status === 'connected') {
		  loginWithAccessToken(response.authResponse.accessToken, 'FACEBOOK');
	    } else {
	      FB.login(function(response) {
	        if (response.authResponse) {
	              loginWithAccessToken(response.authResponse.accessToken, 'FACEBOOK');
	        } else {
	        	$("#loginModal").find(".loader").addClass('hide');
	  			$("#loginModal").find(".modal-body").removeClass('hide');
	  			alert('Please signin by Facebook. Or try with Google signin.');
	        }
	      },{scope: 'email'});
	    }
	  });
}

function logout() {
	gapi.auth.signOut();
	FB.logout(function() {
		logout();
	});
}

// ********************* google login *********************

function googleSignIn() {
	
	$("#loginModal").find(".loader").removeClass('hide');
	$("#loginModal").find(".modal-body").addClass('hide');
	var myParams = {
		'clientid' : gClientId,
		'cookiepolicy' : 'single_host_origin',
		'callback' : 'loginCallback',
		'approvalprompt' : 'auto',
		'accessType' : 'online',
		'scope' : 'profile email',
		'immediate' : true
	};
	gapi.auth.signIn(myParams);
}

function onLoadCallback() {
    gapi.client.setApiKey(gApiKey);
    gapi.client.load('plus', 'v1',function(){});
}

function loginCallback(result) {
	if (result['status']['signed_in']) {
		loginWithAccessToken(gapi.auth.getToken().access_token, 'GOOGLE');
	} else if (result['error'] == "immediate_failed") {
	//      gapi.auth.authorize({
	//    	'clientid' : gClientId,
	////		'cookiepolicy' : 'single_host_origin',
	////		'callback' : 'loginCallback',
	////		'approvalprompt' : 'auto',
	////		'accessType' : 'online',
	//		'scope' : 'profile email',
	//        'immediate' : true
	//    }, function (authRes) {
	//        if (authRes['status']['signed_in']) {
	//        	loginWithAccessToken(gapi.auth.getToken().access_token, 'GOOGLE');
	//        }
	//    });
	} else {
		$("#loginModal").find(".loader").addClass('hide');
		$("#loginModal").find(".modal-body").removeClass('hide');
		alert('Something is not right. Please retry or use Facebook signin.');
	}
}

function loginWithAccessToken(accessToken, client) {
	var dataOb = {
			accessToken : accessToken,
			client : client
	};
	$.ajax({
  	  method: "POST",
  	  url: "/socyal/login/login",
  	  contentType : "application/json",
  	  data: JSON.stringify(dataOb)
  	})
  	  .done(function(response) {
  		  if (response.result) {
  			location.reload();
  		  } else {
  			$("#login-info").text('Login');
  			handleErrorCallback(response);
  			$("#loginModal").find(".loader").addClass('hide');
  			$("#loginModal").find(".modal-body").removeClass('hide');
  		  }		
  	  });
}

function logout() {
	$.ajax({
		method : "GET",
		url : "/socyal/login/logout",
		contentType : "application/json"
	}).done(function(response) {
		location.reload();
	});
}

function manualLogin() {
    var email = $("#m_email").val(),
    password = $("#m_password").val();

    if (!validateEmail(email)) {
        addError('Please check your email');
    } else if (!password) {
        addError('Please enter a password');
    } else {
        addSuccess('Logged in successfully');
    }
}

function register() {
    var name = $("#r_name").val(),
    email = $("#r_email").val(),
    password = $("#r_password").val();

    if (!name) {
        addError('Please enter your name');
    } else if (!validateEmail(email)) {
        addError('Please check your email');
    } else if (!password) {
        addError('Please enter a password');
    } else {
        addSuccess('Successfully Registered');
        // $.ajax({
        //  method : "GET",
        //  url : "/socyal/login/logout",
        //  contentType : "application/json"
        // }).done(function(response) {
        //  location.reload();
        // });
    }
}

function forgotPassword() {
    var email = $("#f_email").val();
    if (!validateEmail(email)) {
        addError('Please check your email');
    } else {
        addSuccess('Please check your email for password');
    }
}

function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

function addError(msg) {
    $(".login-title").addClass('hide');
    $(".login-success").addClass('hide');
    $(".login-error").html(msg);
    $(".login-error").removeClass('hide');
}

function addSuccess(msg) {
    $(".login-title").addClass('hide');
    $(".login-error").addClass('hide');
    $(".login-success").html(msg);
    $(".login-success").removeClass('hide');
}