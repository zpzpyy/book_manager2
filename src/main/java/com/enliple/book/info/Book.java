package com.enliple.book.info;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name="BOOK_LIST")
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 도서 구분번호 */
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	Long id;
	/** 도서명 */
	@Column(name="BOOK_NAME", length = 100, unique = true, nullable = false)
	String bookName;
	/** 저자 */
	@Column(name="WRITER", length = 100, nullable = true)
	String writer;
	/** 출판사 */
	@Column(name="PUBLISHER", length = 30, nullable = true)
	String publisher;
	/** 구매자 */
	@Column(name="BUYER", length = 7, nullable = true)
	String buyer;
	/** 대여자 */
	@Column(name="LENDER", length = 7, nullable = true)
	String lender;
	/** 상태(대여가능/대여중/알수없음) */
	@Column(name="STATE", length = 7, nullable = false)
	@ColumnDefault("대여가능")
	String state;
	/** 가격 */
	@Column(name="PRICE", length = 6, nullable = true)
	String price;
	/** 수정일 */
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="UPT_DATE", nullable = true, columnDefinition = "DATE")
	Date uptDate;
	/** 등록일 */
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="REG_DATE", nullable = false)
	Date regDate;
	/** 비고 */
	@Column(name="INFO", length = 300, nullable = true)
	String info;
	
	public Book( Map<String, String> param ) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ( !ObjectUtils.isEmpty(param.get("id")) ) {
			this.id = Long.parseLong(param.get("id"));
		}
		this.bookName = param.get("bookName");
		this.writer = param.get("writer");
		this.publisher = param.get("publisher");
		this.buyer = param.get("buyer");
		this.lender = param.get("lender");
		this.price = param.get("price");
		this.info = param.get("info");
		if ( !ObjectUtils.isEmpty(param.get("state")) ) {
			this.state = param.get("state");
		} else {
			this.state = "대여가능";
		}
		if ( !ObjectUtils.isEmpty(param.get("regDate")) ) {
			this.regDate = format.parse(param.get("regDate"));
		}
		if ( !ObjectUtils.isEmpty(param.get("uptDate")) ) {
			this.uptDate = format.parse(param.get("uptDate"));
		}
  }

}
