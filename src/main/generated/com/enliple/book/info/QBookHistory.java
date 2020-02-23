package com.enliple.book.info;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookHistory is a Querydsl query type for BookHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBookHistory extends EntityPathBase<BookHistory> {

    private static final long serialVersionUID = -1410551464L;

    public static final QBookHistory bookHistory = new QBookHistory("bookHistory");

    public final NumberPath<Long> bookId = createNumber("bookId", Long.class);

    public final StringPath buyer = createString("buyer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lender = createString("lender");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath state = createString("state");

    public QBookHistory(String variable) {
        super(BookHistory.class, forVariable(variable));
    }

    public QBookHistory(Path<? extends BookHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookHistory(PathMetadata metadata) {
        super(BookHistory.class, metadata);
    }

}

