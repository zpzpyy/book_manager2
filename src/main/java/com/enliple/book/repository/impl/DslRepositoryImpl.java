package com.enliple.book.repository.impl;

import static com.enliple.book.info.QBook.book;
import static com.enliple.book.info.QBookHistory.bookHistory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.enliple.book.info.Book;
import com.enliple.book.info.BookHistory;
import com.enliple.book.info.BookParam;
import com.enliple.book.repository.DslRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DslRepositoryImpl extends QuerydslRepositorySupport implements DslRepository{

	@Autowired
	@Qualifier("jpaQueryFactory")
	JPAQueryFactory jpaQueryFactory;
	
	public DslRepositoryImpl() {
		super(Book.class);
	}

	@Override
	public Page<Book> getBookList( Pageable pageable, BookParam bp ) {
		String sortKey = bp.getSortKey();
		String sortValue = bp.getSortValue();
		
		OrderSpecifier<Long> orderSFL = null;
		OrderSpecifier<String> orderSFS = null;
		OrderSpecifier<Date> orderSFD = null;
		
		if ( "id".equalsIgnoreCase( sortKey ) ) {
			if ( "DESC".equalsIgnoreCase( sortValue )) {
				orderSFL = book.id.desc();
			} else {
				orderSFL = book.id.asc();
			}
		} else if ( "bookName".equalsIgnoreCase( sortKey ) ) {
			if ( "DESC".equalsIgnoreCase( sortValue )) {
				orderSFS = book.bookName.desc();
			} else {
				orderSFS = book.bookName.asc();
			}
		} else if ( "state".equalsIgnoreCase( sortKey ) ) {
			if ( "DESC".equalsIgnoreCase( sortValue )) {
				orderSFS = book.state.desc();
			} else {
				orderSFS = book.state.asc();
			}
		} else if ( "uptDate".equalsIgnoreCase( sortKey ) ) {
			if ( "DESC".equalsIgnoreCase( sortValue )) {
				orderSFD = book.uptDate.desc();
			} else {
				orderSFD = book.uptDate.asc();
			}
		} else if ( "regDate".equalsIgnoreCase( sortKey ) ) {
			if ( "DESC".equalsIgnoreCase( sortValue )) {
				orderSFD = book.regDate.desc();
			} else {
				orderSFD = book.regDate.asc();
			}
		}
		
		QueryResults<Book> result = from( book )
									.where( calSearch( bp ) )
									.orderBy( StringUtils.isEmpty(orderSFL) ? (StringUtils.isEmpty(orderSFS) ? orderSFD : orderSFS ) : orderSFL )
									.limit( pageable.getPageSize() )
									.offset( pageable.getOffset() )
									.fetchResults();
		
		return new PageImpl<Book>(result.getResults(), pageable, result.getTotal());
	}	
	
	@Transactional
	@Override
	public Long updateBook( Map<String, String> params ) throws ParseException {
		Book info = new Book( params );
		JPAUpdateClause updateClause = jpaQueryFactory.update( book );
		
		if( params.containsKey("booName") ){
			updateClause.set( book.bookName, info.getBookName() );
		}
		if( params.containsKey("writer") ){
			updateClause.set( book.writer, info.getWriter() );
		}
		if( params.containsKey("publisher") ){
			updateClause.set( book.publisher, info.getPublisher() );
		}
		if( params.containsKey("buyer") ){
			updateClause.set( book.buyer, info.getBuyer() );
		}
		if( params.containsKey("lender") ){
			updateClause.set( book.lender, info.getLender() );
		}
		if( params.containsKey("price") ){
			updateClause.set( book.price, info.getPrice() );
		}
		if( params.containsKey("state") ){
			updateClause.set( book.state, info.getState() );
		}
		if( params.containsKey("info") ){
			updateClause.set( book.info, info.getInfo() );
		}
		return updateClause.set( book.uptDate, new Date() )
		  				   .where( eqBookId( info.getId()) )
		  			       .execute();
	}
	
	@Transactional
	@Override
	public Long deleteId( Long id ) {
		return delete( book )
			   .where( eqBookId(id) )
			   .execute();
	}
	
	@Override
	public Book findBookId(Long id) {
		return from( book )
			   .where( eqBookId(id) )
			   .fetchOne();
	}
	
	@Override
	public List<BookHistory> findBookHistory(Long id) {
		return from( bookHistory )
			   .where( eqBookIdHistory(id) )
			   .orderBy( bookHistory.regDate.desc())
			   .fetch();
	}
	
	// 조회조건 생성
	private BooleanExpression calSearch( BookParam bp ) {
		
		if( StringUtils.isEmpty( bp.getSearchKey() ) || StringUtils.isEmpty( bp.getSearchValue() ) ) {
			return null;
		} else {	
			BooleanExpression be;
			String searchkey = bp.getSearchKey();
			String searchValue = bp.getSearchValue();
			switch (searchkey) {
				case "bookName": be = containsBookName( searchValue ); break;
				case "writer": be = containsWriter( searchValue ); break;
				case "publisher": be = containsPublisher( searchValue ); break;
				case "buyer": be = containsBuyer( searchValue ); break;
				case "lender": be = containsLender( searchValue ); break;
				case "state": be = eqState( searchValue ); break;
				default: be = containsBookName( searchValue ); break;
			}
			return be;
		}
	}
	
	/** bookId 풀텍스트일치 */
	private BooleanExpression eqBookIdHistory(Long id) {
		if( StringUtils.isEmpty(id)) {
			return null;
		}
		return bookHistory.bookId.eq(id);
	}
	
	/** bookId 풀텍스트일치 */
	private BooleanExpression eqBookId(Long id) {
		if( StringUtils.isEmpty(id)) {
			return null;
		}
		return book.id.eq(id);
	}
	/** bookName 풀텍스트일치 */
	private BooleanExpression eqBookName(String bookName) {
		if( StringUtils.isEmpty(bookName)) {
			return null;
		}
		return book.bookName.eq(bookName);
	}
	/** bookName like */
	private BooleanExpression containsBookName(String bookName) {
		if( StringUtils.isEmpty(bookName)) {
			return null;
		}
		return book.bookName.contains(bookName);
	}
	/** writer 풀텍스트일치 */
	private BooleanExpression eqWriter(String writer) {
		if( StringUtils.isEmpty(writer)) {
			return null;
		}
		return book.writer.eq(writer);
	}
	/** writer like */
	private BooleanExpression containsWriter(String writer) {
		if( StringUtils.isEmpty(writer)) {
			return null;
		}
		return book.writer.contains(writer);
	}
	/** publisher 풀텍스트일치 */
	private BooleanExpression eqPublisher(String publisher) {
		if( StringUtils.isEmpty( publisher )) {
			return null;
		}
		return book.publisher.eq( publisher );
	}
	/** publisher like */
	private BooleanExpression containsPublisher(String publisher) {
		if( StringUtils.isEmpty( publisher )) {
			return null;
		}
		return book.publisher.contains( publisher );
	}
	/** buyer 풀텍스트일치 */
	private BooleanExpression eqBuyer(String buyer) {
		if( StringUtils.isEmpty( buyer )) {
			return null;
		}
		return book.buyer.eq( buyer );
	}
	/** buyer like */
	private BooleanExpression containsBuyer(String buyer) {
		if( StringUtils.isEmpty( buyer )) {
			return null;
		}
		return book.buyer.contains( buyer );
	}
	/** lender 풀텍스트일치 */
	private BooleanExpression eqLender(String lender) {
		if( StringUtils.isEmpty( lender )) {
			return null;
		}
		return book.lender.eq( lender );
	}
	/** lender like */
	private BooleanExpression containsLender(String lender) {
		if( StringUtils.isEmpty( lender )) {
			return null;
		}
		return book.lender.contains( lender );
	}
	
	private BooleanExpression eqState(String state) {
		if( StringUtils.isEmpty( state )) {
			return null;
		}
		return book.state.eq( state );
	}

	
}
