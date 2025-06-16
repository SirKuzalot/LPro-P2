type Node = struct { #l: List, #v: int };
type List = union { #node: Node, #i: int };
type Struct = struct { #x: int, #y: int };

let l:List = #i(5);
let st:Struct = match l {
    #i(x) -> let s:Struct = {#x = 3, #y = 4, #more = 42}; 
            s
|   #node(n) -> let s: Struct = {#x = 2, #y = 5, #less = 42};
            s
};
st;;

