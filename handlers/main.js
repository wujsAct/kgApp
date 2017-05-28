var fortune = require('../libs/fortune.js');
console.log('load js from file');
console.log(fortune.getFortune());

exports.home = function(req,res){
  res.render('home');
};

exports.about = function(req,res){
  res.render('about',{
    fortune:fortune.getFortune(),
  });
};

exports.ner = function(req,res){
  res.render('ner',{csrf:'CSRF token goes here'});
};

exports.process = function(req,res){
  if(req.xhr || req.accepts('json,html')==='json'){
    var http = require('http');
    var querystring = require("querystring");
    var path = '/stanfordParser?';
    var data = querystring.stringify({data:req.body.name});
    var params = path + data;

    var options = {
      hostname:'192.168.3.226',
      port:7777,
      path:params,
      method:'GET'
    };
    var retData = null;
    var reqs = http.request(options,function(resNER){
      console.log('STATUS: '+res.statusCode);
      console.log('HEADERS:'+JSON.stringify(resNER.headers));
      resNER.setEncoding('utf8');
      resNER.on('data',function(chunk){
        console.log(chunk);
        retData = chunk;
        res.send({success:true,retData:retData});
      });
    });
    reqs.end();

  } else {
    res.redirect(303,'/about');
  }
};

var entity = require('../libs/entityData.js');
exports.apiEntityById = function(req,res){
  console.log(req.params.id);
  entity.findById(req.params.id,function(err,a){
    if(err) return res.redirect(500,'/500');
    res.json({
      name:a.name,
      description:a.description
    });
  });
};
