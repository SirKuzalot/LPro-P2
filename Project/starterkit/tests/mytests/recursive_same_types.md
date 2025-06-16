type Node = struct { #l: List, #ol: OtherList, #v: int };
type List = union { #node: Node, #i: int };
type OtherList = union { #node: Node, #i: int };

let ol:OtherList = #i(42);
let l:List = ol;
l;; 