/* fail */
type A = struct { #b: B };
type B = union { #a: int, #b: A };
let b:B = #c(42);
b;;