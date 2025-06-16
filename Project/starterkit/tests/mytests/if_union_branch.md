type Node = struct { #l: List, #v: int };
type List = union { #node: Node, #i: int };
type OtherList = union { #node: Node, #i: int };

let mklist: int -> List = (
    fn n:int => {
        if (n == 0) { #i(0) }
        else { #node({ #l = mklist(n-1), #v = n })}
    });

let sumlist: List -> int = (
    fn l:List => {
        match l {
            #i(x) -> x
        |   #node(n) -> n.#v + sumlist(n.#l)
        }
    });

let ol:OtherList = #i(42);


sumlist(ol);;