let reduce: (int -> int -> int) -> int -> int -> int = 
fn g:int->int->int, b:int, k:int => {
    if (k == 0) { b }
    else {
         g (k) (reduce (g) (b) (k-1) )
      }
  };
let fact = reduce (fn n:int, p:int => { n*p }) (1) ;
fact (10);;
