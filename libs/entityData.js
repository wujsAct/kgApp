//javascript function styple
this.findById = function(Id,callback){
  var entityList={'1':{'name':'Zhang Ziyi','description':'is a actress','type':'actress'},
                  '2':{'name':'Zhao Wei','description':'is a actress','type':'actress'}
                 };
  err = true;
  retdata = null;
  if (Id in entityList){
    err = false;
    retdata = entityList[Id];
  }
  callback(err,retdata);
};
