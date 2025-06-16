
type Struct = struct { #x: int, #y: int };

let mklist: int -> Struct = (
    fn n:int => {
        if (n == 0) { {#x = 1, #y = 2, #some = 3} }
        else { {#x = 3, #y = n + 1, #more = 4} }
    });

let s:Struct = mklist(5);

s;;