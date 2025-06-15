let fact:int->int =
    fn n:int => {
        if (n==0) { 1 }
        else { n * (fact (n-1))}
    };
fact (5);;
