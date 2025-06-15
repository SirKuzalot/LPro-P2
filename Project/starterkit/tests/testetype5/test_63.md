/* fails */
type Opt = union { #none:(), #some:int};
let l0:Opt = #none(());
match l0 {
    #some(x) -> x + 1
};;
