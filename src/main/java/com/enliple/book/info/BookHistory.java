package com.enliple.book.info;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="BOOK_HISTORY")
public class BookHistory implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	Long id;
	/** BookId */
	@Column(name="BOOK_ID", length = 12, nullable = false)
	Long bookId;
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
	/** 등록일 */
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="REG_DATE", nullable = false)
	Date regDate;
	
	public BookHistory(Map<String, String> param){
		if ( !ObjectUtils.isEmpty(param.get("id")) ) {
			this.bookId = Long.parseLong(param.get("id"));
		}
		this.buyer = param.get("buyer");
		this.lender = param.get("lender");
		if ( !ObjectUtils.isEmpty(param.get("state")) ) {
			this.state = param.get("state");
		} else {
			this.state = "대여가능";
		}
  }

}
