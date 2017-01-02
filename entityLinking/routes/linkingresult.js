var express = require('express');
var router = express.Router();


/* GET users listing. */
router.get('/:linkingtext', function(req, res, next) {
  res.render('linkingresult', {
    linkingtext:req.params.linkingtext
  });
});

module.exports = router;
