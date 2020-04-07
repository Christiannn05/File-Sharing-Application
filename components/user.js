//
// REST API

const express = require("express");
const router = express.Router();
const bodyParser = require("body-parser");
const User = require("./userModel");
const urlencodedParser = bodyParser.urlencoded({
  extended: false
});

const uuidv4 = require('uuid/v4');
const jwt = require("jsonwebtoken");
const auth = require('../../middleware/auth');

router.post("/register", (req, res, next) => {
  console.log(req.body);
  req.body.fingerprint = uuidv4();
  const user = User(req.body);
  user.save((error, document) => {
    if (error) {
      console.log(error);
      res.status(403).send({
        message: error
      })
    }
    if (document) {
      // res.redirect("/home");
      // sign tokeen hetre
      // user.token = nunfuadfu
      // const privateKey = "secret";
      jwt.sign({ user: user }, process.env.JWT_KEY,  {expiresIn: 1800}, function(err, token) {
        if(err) console.log(err)
        console.log(token);
        res.status(201).send({token: token});
      });
    
    }
  })
});

router.get("/list", auth, (req, res, next) => {
  console.log("Endpoint");
  User.find({
    credit: 0
  }, (err, docs) => {
    if (docs.length > 0) {
      res.send(docs);
    } else {
      res.status(404);
    }
  });
});


router.get("/logout", (req, res, next) => {
  if (req.session.admin && req.cookies.user_sid) {
    res.clearCookie('user_sid');
    res.redirect('/admin/login');
  } else {
    res.redirect('/admin/login');
  }
});


router.post("/login", (req, res, next) => {
  if(req.body.fingerprint){
    User.find({
      fingerprint: req.body.fingerprint
    }).then(document => {
      if(document.length === 0){
        res.status(404).send({error: "Not found"});
      }else{
        res.status(200).send(document);
      }
    }).catch(reason => {
      res.status(401).send(reason);
    });
  }else{
    res.status(401).send({error: "Fingerprint required"});
  }
});

router.post('/upload', function(req, res) {
  console.log('upload route');
  if (Object.keys(req.files).length == 0) {
    return res.status(400).send('No files were uploaded.');
  }

  // The name of the input field (i.e. "sampleFile") is used to retrieve the uploaded file
  let file = req.files.file;
  console.log(file);
  // Use the mv() method to place the file somewhere on your server
  file.mv('files/' + file.name, function(err) {
    console.log(err);
    if (err)
      return res.status(500).send(err);

    res.send({status: 'FILE_UPLOADED'});
  });
});


module.exports = router;