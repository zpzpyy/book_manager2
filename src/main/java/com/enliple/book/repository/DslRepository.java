package com.enliple.book.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.enliple.book.info.Book;
import com.enliple.book.info.BookHistory;
import com.enliple.book.info.BookParam;

public interface DslRepository{

	Page<Book> getBookList( Pageable pageable, BookParam bookParam ); 
	
	Long updateBook( Map<String, String> params ) throws ParseException ;

	Book findBookId( Long id );
	
	List<BookHistory> findBookHistory( Long id );
	
	Long deleteId( Long id );
	
}
