/* fail */
type Person = struct{ #name:int, #age: int };
let p0:Person = {#name = 1};
p0.#name + p0.#age;;
