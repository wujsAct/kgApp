var express = require('express');
var router = express.Router();


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', {
    title: 'Home',
    success:false,
    errors:req.session.errors
  });
});

router.post('/linkingresult',function(req,res,next){
  /**
  @time: 2017/1/2
  */
  //Get Form values
  var linkingtext = req.body.linkingtext;
  console.log(linkingtext);
  res.redirect('/linkingresult/'+linkingtext);   //res.end(JSON.stringify(req.body))


  //Form validation
  req.checkBody('linkingtext','linkingText file is required').notEmpty();
  //check errors
  //var errors = req.validationErrors();
  //console.log(errors);
  /**
  if (erros){
    res.render('linkingText',{
      'errors':errors;
      'linkingText': linkingText;
    });
  } else{
    console.log(linkingText);
  }
*/
});

module.exports = router;
