var isLoggedIn = false;

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
    var provider = new firebase.auth.GoogleAuthProvider();
    // provider.addScope('https://www.googleapis.com/auth/plus.login');
    firebase.auth().signInWithPopup(provider).then(function(result) {
      var token = result.credential.accessToken;
      var user = result.user;
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
    var provider = new firebase.auth.FacebookAuthProvider();
    // provider.addScope('user_birthday');
    firebase.auth().signInWithPopup(provider).then(function(result) {
      var token = result.credential.accessToken;
      var user = result.user;
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
	isLoggedIn = false;
}

function initApp() {
  firebase.auth().onAuthStateChanged(function(user) {
	  console.log('called onAuthStateChanged');
    if (user) {
      firebase.auth().currentUser.getToken(/* forceRefresh */ true).then(function(idToken) {
		  // Send token to your backend via HTTPS
    	  loginWithIdToken(idToken);
		}).catch(function(error) {
		  // Handle error
		});
      var displayName = user.displayName;
      $("#login-info").text(displayName);
      isLoggedIn = true;
      $("#loginModal").modal('hide');
    } else {
      $("#login-info").text('Login');
      isLoggedIn = false;
    }
  });
}

function loginWithIdToken(idToken) {
	var dataOb = {
			idToken : idToken
	};
	$.ajax({
  	  method: "POST",
  	  url: "/socyal/login/login",
  	  contentType : "application/json",
  	  //beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + accessToken); },
  	  data: JSON.stringify(dataOb)
  	})
  	  .done(function(response) {
  		  console.log(response);
  	  });
}