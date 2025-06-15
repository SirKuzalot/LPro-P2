type Person = struct{ #name:int, #age: int };
let p0:Person = {#name = 1, #age = 2, #more = 42};
p0.#name + p0.#age;;
