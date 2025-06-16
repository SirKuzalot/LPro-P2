type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let n:NODE = { #val = 1, #next = #nil(()), #extra = 42 };
n;;