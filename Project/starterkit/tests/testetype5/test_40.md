let inc = fn r:ref<int>,z:int->int => { r := z(!r + 1); !r};
let age = box (1);
inc (age) (fn x:int => { x + 1 });;
