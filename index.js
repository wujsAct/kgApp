var express = require('express');

var app = express();
var $ = require('jQuery');
var handlebars = require('express3-handlebars').create({
              defaultLayout:'main',
              helpers:{
                section:function(name,options){
                  if(!this._sections) this.sections={};
                  this._sections[name] = options.fn(this);
                  return null;
                }
              }
            });
app.engine('handlebars',handlebars.engine);
app.set('view engine','handlebars');

app.set('port',process.env.PROT||3000);
app.use(express.static(__dirname+'/public'));  //import credentials into


app.use(require('body-parser')());
var credentials = require('./credentials.js');
app.use(require('cookie-parser')(credentials.cookieSecret));

app.use(function(req,res,next){
    res.locals.showTests = app.get('env') !=='production' && req.query.test ==='1';
    next();
});

require('./routes.js')(app);
app.listen(app.get('port'),function(){
  console.log('Express startd on http://localhost:'+
    app.get('port')+'; press Ctrl-c to terminate.');
});
