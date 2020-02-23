package com.enliple.book.info;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class BookParam implements Serializable{

	private static final long serialVersionUID = 1L;
	Long id;
	String bookName;
	String writer;
	String publisher;
	String buyer;
	String lender;
	String state;
	String price;
	Date uptDate;
	Date regDate;
	String info;
	String sortKey;
	String sortValue;
	String searchKey;
	String searchValue;
	
	
	public BookParam(Map<String, String> param) {
		if ( !ObjectUtils.isEmpty(param.get("id")) ) {
			this.id = Long.parseLong(param.get("id"));
		}
		this.bookName = param.get("bookName");
		this.writer = param.get("writer");
		this.publisher = param.get("publisher");
		this.buyer = param.get("buyer");
		this.lender = param.get("lender");
		this.state = param.get("state");
		this.price = param.get("price");
//		this.uptDate = param.get("upt_date");
//		this.regDate = param.get("reg_date");
		this.info = param.get("info");
		this.sortKey = param.get("sortKey");
		this.sortValue = param.get("sortValue");
		this.searchKey = param.get("searchKey");
		this.searchValue = param.get("searchValue");
	}
	
	
	
}
