package com.enliple.book.info;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 64078748L;

    public static final QBook book = new QBook("book");

    public final StringPath bookName = createString("bookName");

    public final StringPath buyer = createString("buyer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final StringPath lender = createString("lender");

    public final StringPath price = createString("price");

    public final StringPath publisher = createString("publisher");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath state = createString("state");

    public final DateTimePath<java.util.Date> uptDate = createDateTime("uptDate", java.util.Date.class);

    public final StringPath writer = createString("writer");

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

