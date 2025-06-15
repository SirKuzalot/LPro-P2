let x = 1;
let f = fn y:int => { x + y };
(
let x = 4;
(x + f (2))
);;
