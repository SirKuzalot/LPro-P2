type MPair = struct { #fst: ref<int>, #snd:ref<int>};
let p1 = { #fst = box(1), #snd = box(2)};
p1.#fst := *(p1.#snd);
p1.#snd := *(p1.#snd) + 1;
*(p1.#fst) + *(p1.#snd);;
