let x=1 ;			 
let f = fn y:int => { y+x } ;
let g = fn x:int => { x+f(x) };
g(2);;
