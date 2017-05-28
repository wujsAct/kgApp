var assert = require('chai').assert;
var http = require('http');
var rest = require('restler');

suite('API tests',function(){
  var entityDemo = {
    name: 'Obama',
    description:'Zhang Ziyi is a chinese actress and model.',
    type:'person'
  };

  var base = 'http://localhost:3000';

  test('should be able to retrieve an entity',function(done){
    rest.post(base+'/api/entity',{data:entityDemo}).on('success',
      function(data){
      rest.get(base+'/api/entity/'+data.id).on('success',
          function(data){
            assert(data.name === entityDemo.name);
            assert(data.description === entityDemo.description);
            done();
          });
    });
  });
});
