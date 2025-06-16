/* fail */
let f = fn g:int->bool,z:int => { g (z) };
f (fn z:int => { z*2 }) (7);;
