type ICounter = struct { #inc: () -> int, #get : () -> int };
let newcounter:int -> ICounter =
    fn n:int =>
        { let v = box(n);
            { 
                #inc = fn _:() => { v := !v + 1 }, 
                #get = fn _:() => { !v }
            }
        };
let c0 = newcounter(0);
    c0.#inc(); 
    c0.#inc();
    c0.#get();;
