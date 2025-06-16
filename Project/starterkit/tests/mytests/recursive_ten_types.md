type T1 = union { #t2: T2, #i: int };
type T2 = union { #t3: T3, #i: int };
type T3 = union { #t4: T4, #i: int };
type T4 = union { #t5: T5, #i: int };
type T5 = union { #t6: T6, #i: int };
type T6 = union { #t7: T7, #i: int };
type T7 = union { #t8: T8, #i: int };
type T8 = union { #t9: T9, #i: int };
type T9 = union { #t10: T10, #i: int };
type T10 = union { #t1: T1, #i: int };

let v:T1 = #t2(
    #t3(
        #t4(
            #t5(
                #t6(
                    #t7(
                        #t8(
                            #t9(
                                #t10(
                                    #t1(
                                        #i(42)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )
);

v;;