package com.enliple.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enliple.book.info.BookHistory;

@Repository
public interface BookHistoryJpaRepository extends JpaRepository<BookHistory, Long>{

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query( value = "DELETE FROM dreamsearch.BOOK_HISTORY "
			 	+   "WHERE  BOOK_ID = :bookId "	, nativeQuery=true)
	void deleteBookHistory( @Param("bookId") Long bookId );
	
}
