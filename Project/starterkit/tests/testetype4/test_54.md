type ICounter = struct { #inc: () -> int, #get : () -> int };
type IIncView = struct { #inc: () -> int };
let newcounter:int -> ICounter =
    fn n:int =>
        { let v = box(n);
            { 
                #inc = fn _:() => { v := *v + 1 }, 
                #get = fn _:() => { *v }
            }
        };
let c0 = newcounter(0);
let client = fn r: IIncView  =>
            { r.#inc(); r.#inc(); r.#inc() };
    client (c0);
    c0.#get();;
