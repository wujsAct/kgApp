var http = require('http');
var querystring = require("querystring");
var path = '/stanfordParser?';
var data = querystring.stringify({data:"wujs is a good girl..."});
var params = path + data;

var options = {
  hostname:'192.168.3.226',
  port:7777,
  path:params,
  method:'GET'
};

var req = http.request(options,function(res){
  console.log('STATUS: '+res.statusCode);
  console.log('HEADERS:'+JSON.stringify(res.headers));
  res.setEncoding('utf8');
  res.on('data',function(chunk){
    console.log(chunk);
  });
});
req.end();
