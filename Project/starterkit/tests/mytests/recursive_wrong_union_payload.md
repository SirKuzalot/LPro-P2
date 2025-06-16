/* fail */
type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let l:LIST = #nil(42);
l;;