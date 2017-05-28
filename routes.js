//项目的所有路由都设置在这个地方！非常方便
var main = require('./handlers/main.js');
module.exports = function(app){
 app.get('/',main.home);
 app.get('/about',main.about);
 app.get('/ner',main.ner);
 app.post('/process',main.process);
 app.get('/api/entity/:id',main.apiEntityById);
  //定制404页面
  app.use(function(req,res){
    res.status(400);
    res.render('404');
  });

  //定制500页面
  app.use(function(err,req,res,next){
    console.error(err.stack);
    res.status(500);
    res.render('500');
  });
};
