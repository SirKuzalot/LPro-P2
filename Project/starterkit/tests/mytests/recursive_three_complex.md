type TNODE = struct {
        #val:int,
        #left:ref<BTREE>,
        #right:ref<BTREE>
};

type BTREE = union {
        #nil: (),
        #list: TLIST
};

type TLIST = union {
        #nil: (),
        #node: TNODE
};

type RTREE = ref<BTREE>;

let mknil: BTREE = #nil(());

let mknode:int->BTREE =
    fn n:int => {
        #list(
            #node({
                #val = n,
                #left = box(mknil), 
                #right = box(mknil)
            })
        )
    };

let insert: RTREE->int->() = 
    fn t:RTREE, n:int =>
    {
        match *t {
            #nil(_) -> 
                t := mknode(n); ()
        |   #list(l) ->
            match l {
                #node(c) ->
                    if (n < c.#val)
                        { insert(c.#left)(n) }
                    else
                        { insert(c.#right)(n) }
            |   #nil(_) -> ()
            }
        }
    };

let inorder: RTREE->() = 
    fn t:RTREE =>
    {
        match *t {
            #nil(_) -> ()
        |   #list(l) ->
            match l {
                #node(c) ->
                    inorder(c.#left);
                    println(c.#val);
                    inorder(c.#right)
            |   #nil(_) -> ()
            }
        }
    };

let tree:RTREE = box(mknil);
insert(tree)(5);
insert(tree)(3);
insert(tree)(10);
insert(tree)(8);
insert(tree)(7);
insert(tree)(2);
insert(tree)(9);
inorder(tree);;