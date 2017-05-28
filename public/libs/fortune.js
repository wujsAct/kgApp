var fortune =["conquer your fears or they will conquer you",
                "Rivers need springs"];

function getFortune(){
  return fortune[Math.floor(Math.random()*fortune.length)];
}
