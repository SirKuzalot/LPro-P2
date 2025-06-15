type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3;
let area = fn b:Blob => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1 = #circle({ #cx=0, #cy=0, #rad=2 });
area(r1);;
