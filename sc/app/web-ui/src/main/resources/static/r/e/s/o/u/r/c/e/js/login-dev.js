var fbClientId = '232637110490690';
var gApiKey = 'AIzaSyCWEn4qyHStxlVn2BF9EO9JfhxMsCRsMR4';
var gClientId = '504868894775-jbme613s3rdb80jf95o44cmpeut11q9c.apps.googleusercontent.com';

window.fbAsyncInit = function() {
	FB.init({
		appId : fbClientId,
		xfbml : true,
		version : 'v2.9'
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
  FB.getLoginStatus(function(response) {
    if (response.status === 'connected') {
      console.log('already logged in, uid : ' + response.authResponse.userID);
      var uid = response.authResponse.userID;
      var accessToken = response.authResponse.accessToken;
    } else {
      FB.login(function(response) {
        if (response.authResponse) {
              console.log('Login with fb');
              loginWithAccessToken(response.authResponse.accessToken, 'FACEBOOK');
        } else {
           console.log('User cancelled login or did not fully authorize.');
        }
      },{scope: 'email'});
    }
  });
}

//function fbLogout() {
//	FB.logout(function() {
//		logout();
//	});
//}

function logout() {
	gapi.auth.signOut();
	FB.logout(function() {
		logout();
	});
}

// ********************* google login *********************

function googleSignIn() 
{
  var myParams = {
    'clientid' : gClientId,
    'cookiepolicy' : 'single_host_origin',
    'callback' : 'loginCallback',
    'approvalprompt':'force',
    'scope' : 'profile email'
  };
  gapi.auth.signIn(myParams);
}

function onLoadCallback() {
    gapi.client.setApiKey(gApiKey);
    gapi.client.load('plus', 'v1',function(){});
}

function loginCallback(result) {
	if (result['status']['signed_in']) {
		console.log('Login with google');
		loginWithAccessToken(gapi.auth.getToken().access_token, 'GOOGLE');
	} else {
		alert('Something is not right. Please try again after refreshing the page');
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