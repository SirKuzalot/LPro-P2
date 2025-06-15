type knott = ref<int->int>;
let knot:knott = box (fn x:int => {x});
let fact = fn n:int => {
      if (n==0) { 1}
        else { n * ((!knot)( n - 1 ))}};
knot := fact;
fact (6);;
