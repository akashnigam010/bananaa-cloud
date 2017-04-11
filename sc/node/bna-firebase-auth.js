var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var path = require('path');
var pathToServiceAccountKey = path.resolve(__dirname, 'firebase-dev.json');

app.use(bodyParser.json());

app.use(express.static('js'));

var serviceAccount = require(pathToServiceAccountKey);

var admin = require("firebase-admin");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});


app.post('/verifyIdToken', function (req, res) {
	admin.auth().verifyIdToken(req.body.idToken).then(function(decodedToken) {
		var uid = decodedToken.uid;
		console.log('UID retrieved: ' + uid);
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify({ "uid" : uid, "status" : true }));
	}).catch(function(error) {
		console.log('Error occurred while verifying id token: ' + error);
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify({ "status" : false }));
	});
});

app.post('/getUserDetails', function (req, res) {
	var uid = req.body.uid;
	admin.auth().getUser(uid).then(function(userRecord) {
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify({ "user" : userRecord, "status" : true }));
	})
	.catch(function(error) {
		console.log("Error occurred while fetching user data:", error);
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify({ "status" : false }));
	});
});

var server = app.listen(8443, function () {
  console.log("Node server up and running on port: " + server.address().port);
})