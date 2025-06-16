type Node = struct { #l: List, #v: int };
type List = union { #tree: Tree, #i: int };
type Tree = union { #node: Node, #empty: () };

let t:Tree = #node({ #l = #tree(#empty(())), #v = 42 });

t;;