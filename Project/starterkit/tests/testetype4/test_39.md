type Iaccessor = ref<int> -> ref<int> -> int;
type Igettype = (Iaccessor -> int)->int;
type Isettype = (Iaccessor -> int) -> int -> int;

let mkpair =
    fn a:int,b:int => { 
        let l = box(a);
        let r = box(b);
        fn f:Iaccessor => { f (l) (r) }
};
let getfst:Igettype =
    fn p:Iaccessor->int =>
        { p (fn a:ref<int>,b:ref<int> => { *a }) };

let getsnd:Igettype =
    fn p:Iaccessor->int =>
        { p (fn a:ref<int>,b:ref<int> => { *b })};

let setfst:Isettype  =
    fn p:Iaccessor->int,v:int =>
        { p (fn a:ref<int>,b:ref<int> => { a := v })};

let setsnd:Isettype =
    fn p:Iaccessor->int,v:int =>
        { p (fn a:ref<int>,b:ref<int> => { b := v })};

let x = mkpair (1) (2);
    setfst (x) (10);
    setsnd (x) (20);
    (getfst (x)) + (getsnd (x));;
