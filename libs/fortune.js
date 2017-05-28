//javascript function styple
this.getFortune = function(){
  var fortune=["conquer your fears or they will conquer you","Rivers need springs"];
  return fortune[Math.floor(Math.random()*fortune.length)];
};
