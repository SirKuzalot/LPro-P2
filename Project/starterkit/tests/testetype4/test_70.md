type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:union{ #rect: Rectangle } => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1 = #rect({ #h = 10, #w = 20 });
area(r1);;
