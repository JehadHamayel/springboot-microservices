package org.ms.post.mappers;

public interface Mapper <A,B>{

    B mapTo(A a);

    A mapFrom(B b);

}
