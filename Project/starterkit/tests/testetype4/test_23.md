let x=1 ;			 
let f = fn y:int => {
           let k = x*2;
  	   y+x*k
        };
let g = fn x:int, u:int->int =>
           { u(x) + f(x) };
g  (f(3)) (f);;
