type Opt = union { #none:(), #some:int};
let l0:Opt = #none(());
match l0 {
    #none(_) -> 1
|   #some(i) -> i    
};;
