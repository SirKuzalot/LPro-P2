type Node = struct { #l: List, #v: int };
type List = union { #node: Node, #i: int };

let l:List = #i(5);
match l {
    #i(x) -> #i(x)
|   #node(n) -> #node(n)
};

l;;