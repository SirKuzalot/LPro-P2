let k = -1;
let om: (int -> int)->int = fn f:int->int => { f (k)};
om (fn k:int => {k*k});;
