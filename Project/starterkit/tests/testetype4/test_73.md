type NODE = struct {
        #val:int,
        #next:LIST
};
type LIST = union {
        #nil: (),
        #cons: NODE
};
let l0:LIST = #nil (()) ;
let l1:LIST = #cons ( { #val=2, #next=l0}) ;
let l2:LIST = #cons ( { #val=3, #next=l1}) ;
let plist:LIST->() = fn
    l:LIST => { 
        match l {
            #nil(_) -> println (-1000); ()
        |   #cons(n) -> 
                print (n.#val);
                plist (n.#next)
        }
    }
;
plist (l2);;
