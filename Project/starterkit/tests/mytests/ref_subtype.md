/* fails */
type NODE = struct { #val: int, #next: LIST };
type LIST = union { #tree: TREE, #nil: () };
type TREE = union { #node: NODE, #empty: () };
type RTREE = ref<TREE>;

let bad: ref<union { #empty: () }> = box(#empty(()));
let tree: RTREE = bad;

tree;;