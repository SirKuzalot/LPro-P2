type NODE = struct {
        #val:int,
        #next:LIST
};

type LIST = union {
        #nil: (),
        #cons: NODE
};

let cons: int -> LIST -> LIST =
    fn v:int, l:LIST =>
        {#cons ( {#val=v, #next=l})};

let concat:LIST->LIST->LIST =
    fn a:LIST, b:LIST => { 
        match a {
            #nil(_) -> b
        |   #cons(n) -> 
                cons (n.#val) (concat (n.#next) (b))
        }
    };

let genlist: int -> LIST =
    fn n:int => {
        if (n==0) { #nil(()) }
        else {
            cons (n) (genlist (n-1))
        }
    };

let print_list:LIST->() = fn
    l:LIST => { 
        match l {
            #nil(_) -> ()
        |   #cons(n) -> 
                println (n.#val);
                print_list (n.#next)
        }
    }
;

let l0 = genlist (100);
let la = concat (l0) (l0);
print_list (la);;
