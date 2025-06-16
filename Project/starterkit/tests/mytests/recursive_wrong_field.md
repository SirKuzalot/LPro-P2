/* fail */
type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let l:LIST = #cons({ #val = 1, #next = 42 });
l;;