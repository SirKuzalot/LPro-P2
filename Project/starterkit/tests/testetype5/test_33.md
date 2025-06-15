let c = 0;
let L = 1000;
let m = box(L);
let S = box(c);
(
while (!m>0) {
    m := !m - 1;
    S := !S + !m
};
!S
);;
