type i2i = int->int;
type refi2i = ref<i2i>;
let sigfpe:refi2i = box ( fn x:int => {x} );
let setfpe = fn h:i2i => { sigfpe := h };
let div = fn n:int,m:int => {
      if (m==0) { (*sigfpe) (n) }
        else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0);;
