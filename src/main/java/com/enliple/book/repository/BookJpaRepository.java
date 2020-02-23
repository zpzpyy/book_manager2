package com.enliple.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enliple.book.info.Book;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long>{

}
