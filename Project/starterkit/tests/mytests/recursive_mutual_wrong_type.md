/* fail */
type A = struct { #b: B };
type B = union { #a: int, #b: A };
let a:A = { #b = #a({ #b = #a(42) }) };
a;;