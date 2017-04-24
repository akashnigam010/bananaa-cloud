var config = {
	apiKey : "AIzaSyA5PvxQeVFkwJWObrJ_hflHmBOCMKycK4w",
	authDomain : "testsignin-160611.firebaseapp.com",
	databaseURL : "https://testsignin-160611.firebaseio.com",
	storageBucket : "testsignin-160611.appspot.com",
	messagingSenderId : "504868894775"
};
firebase.initializeApp(config);

function googleSignIn() {
  if (!firebase.auth().currentUser) {
	$("#loginModal").find(".loader").removeClass('hide');
	$("#loginModal").find(".modal-body").addClass('hide');
    var provider = new firebase.auth.GoogleAuthProvider();
    provider.addScope('profile');
    provider.addScope('email');
    firebase.auth().signInWithPopup(provider).then(function(result) {
      loginWithIdToken();
    }).catch(function(error) {
      var errorCode = error.code;
      var errorMessage = error.message;
      var email = error.email;
      var credential = error.credential;
      if (errorCode === 'auth/account-exists-with-different-credential') {
        alert('You have already signed up with Facebook, please use the same method again to login.');
      } else {
        console.error(error);
      }
    });
  } else {
    firebase.auth().signOut();
  }
}

function fbSignIn() {
  if (!firebase.auth().currentUser) {
	$("#loginModal").find(".loader").removeClass('hide');
	$("#loginModal").find(".modal-body").addClass('hide');
    var provider = new firebase.auth.FacebookAuthProvider();
    provider.addScope('email');
    firebase.auth().signInWithPopup(provider).then(function(result) {
    	loginWithIdToken();
    }).catch(function(error) {
      var errorCode = error.code;
      var errorMessage = error.message;
      var email = error.email;
      var credential = error.credential;
      if (errorCode === 'auth/account-exists-with-different-credential') {
    	  alert('You have already signed up with Google, please use the same method again to login.');
      } else {
        console.error(error);
      }
    });
  } else {
    firebase.auth().signOut();
  }
}

function logout() {
	firebase.auth().signOut();
	$.ajax({
	  	  method: "GET",
	  	  url: "/socyal/login/logout",
	  	  contentType : "application/json"
	  	})
	  	  .done(function(response) {
	  		  location.reload();
	  	  });
}

function loginWithIdToken(idToken) {
	firebase.auth().currentUser.getToken(true).then(function(idToken) {
		var dataOb = {
				idToken : idToken
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
	}).catch(function(error) {
		$("#loginModal").find(".loader").addClass('hide');
		$("#loginModal").find(".modal-body").removeClass('hide');
		alert('Something is not right, please try again after sometime.');
	});
}