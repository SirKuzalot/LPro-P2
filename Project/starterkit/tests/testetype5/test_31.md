type int2op = (int -> int -> int) ;
type redtype = int2op -> int -> int -> int;
let reduce: redtype = 
fn g:int2op, b:int, k:int  => {
    if (k == 0) { b }
    else {
         g (k) (reduce (g) (b) (k-1) )
      }
  };
let tri = reduce (fn n:int, p:int => { println (n+p) }) (1) ;
tri (100);;
