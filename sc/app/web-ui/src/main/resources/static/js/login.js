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
        alert('You have already signed up with a different auth provider for that email.');
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
        alert('You have already signed up with a different auth provider for that email.');
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
    if (user) {
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