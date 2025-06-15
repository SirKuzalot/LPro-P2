type i2i = int->int;
let sigfpe:ref<int->int> = box ( fn x:int => {x} );
let setfpe = fn h:int->int => { sigfpe := h };
let div = fn n:int,m:int => {
      if (m==0) { (!sigfpe) (n) }
        else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0);;
