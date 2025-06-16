/* fail */
type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let l:LIST = #cons(42);
l;;