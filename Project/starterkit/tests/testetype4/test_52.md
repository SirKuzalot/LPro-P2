type ICounter = struct { #inc: () -> int, #get : () -> int };
let c:int -> ICounter =
    fn n:int => { let v = box(n);
                { #inc = fn _:() => { v := *v + 1 }, 
                  #get = fn _:() => { *v } }};
let cv = c(0);
cv.#inc(());
cv.#inc(());
cv.#get(());;
