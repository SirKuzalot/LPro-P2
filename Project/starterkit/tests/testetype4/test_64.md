type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let r1 = #rect({ #h = 10, #v = 20 });
match r1 {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#v
};;
