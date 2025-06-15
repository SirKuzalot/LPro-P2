type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let l0:LIST = #nil (()) ;
let l1:LIST = #cons ( { #val=2, #next=l0}) ;
let l2:LIST = #cons ( { #val=3, #next=l1}) ;
l1;;
