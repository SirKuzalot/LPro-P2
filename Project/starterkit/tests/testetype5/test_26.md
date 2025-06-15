let fact:int->int = (
    let one = 1;
    fn n:int => {
        if (n==0) { one }
        else { n * (fact (n-1))}
    });
fact (5);;
