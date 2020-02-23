package com.enliple.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enliple.book.info.Book;
import com.enliple.book.info.BookHistory;
import com.enliple.book.info.BookParam;
import com.enliple.book.repository.BookHistoryJpaRepository;
import com.enliple.book.repository.BookJpaRepository;
import com.enliple.book.repository.DslRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api( value="BookManager API")
@RequestMapping("/api")
@RestController
public class BookController {

	@Autowired
	DslRepository dslRepository;
	
	@Autowired
	BookJpaRepository bookJpaRepository;
	
	@Autowired
	BookHistoryJpaRepository bookHistoryJpaRepository;
	
	int startId, endId, currentId;
	int pageBlockSize = 5;
	

	/** book list  */
	@ApiOperation(value = "도서 리스트 조회", notes = "도서 리스트 출력 API")
	@ApiImplicitParams({
		@ApiImplicitParam(name="size", value="한 페이지에 보여줄 Row수", required = true, dataType = "int", paramType = "query", defaultValue = "10" ),
		@ApiImplicitParam(name="page", value="요청페이지번호(시작:0)", required = true, dataType = "int", paramType = "query", defaultValue = "0" ),
		@ApiImplicitParam(name="searchKey", value="조회기준 컬럼", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="searchValue", value="조회 Value", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="sortKey", value="정렬기준 컬럼", required = false, dataType = "string", paramType = "query", defaultValue = "id" ),
		@ApiImplicitParam(name="sortValue", value="정렬값(ASC:오름차순/DESC:내림차순)", required = false, dataType = "string", paramType = "query", defaultValue = "ASC" )
	})
	@RequestMapping(value = "/book", produces="application/json; charset=utf8", method = RequestMethod.GET )
	public @ResponseBody Map<String,Object> bookList( 
													  @RequestParam(required = true) int size,
													  @RequestParam(required = true) int page,
													  @RequestParam(required = false) String searchKey,
													  @RequestParam(required = false) String searchValue,
													  @RequestParam(required = false) String sortKey,
													  @RequestParam(required = false) String sortValue
			){
		log.info("[bookList] page:{} size:{} sortKey:{} sortValue:{} searchKey:{} searchValue:{}",page,size,sortKey,sortValue,searchKey,searchValue);
		
		Map<String,Object> result = new HashMap<String, Object>();
//		PageRequest pageRequest = PageRequest.of( Integer.parseInt(param.get("page")), Integer.parseInt(param.get("size")) );
		
		page =  StringUtils.isEmpty( page ) ? 0 : page;
		size =  StringUtils.isEmpty( size ) ? 10 : size;
		sortKey =  StringUtils.isEmpty( sortKey ) ? "id" : sortKey;
		sortValue =  StringUtils.isEmpty( sortValue ) ? "ASC" : sortValue;
		
		PageRequest pageRequest = PageRequest.of( page, size );

		Map<String, String> param = new HashMap<String, String>();
		param.put( "sortKey", sortKey );
		param.put( "sortValue", sortValue );
		param.put( "searchKey", searchKey );
		param.put( "searchValue", searchValue );
		
		BookParam bp = new BookParam( param );
		// BookList 요청
		Page<Book> list = dslRepository.getBookList( pageRequest, bp );
		
		calculate( list );
		
		// 조회 K/V
		if( !StringUtils.isEmpty( bp.getSearchKey() ) && !StringUtils.isEmpty( bp.getSearchValue() ) ) {
			result.put( "searchKey", bp.getSearchKey() );
			result.put( "searchValue", bp.getSearchValue() );
		}
		
		// 정렬 K/V
		result.put( "sortKey", bp.getSortKey() );
		result.put( "sortValue", bp.getSortValue() );
		
		result.put( "endId", endId-1);
		result.put( "startId", startId-1);
		result.put( "currentId", list.getNumber());
		result.put( "paging", list);
		
		return result;
	}
	
	/** book regist  */
	@ApiOperation(value = "도서 등록", notes = "도서 등록 API")
	@ApiImplicitParams({
		@ApiImplicitParam(name="params", value="입력하지 않아도 되는 필드", required = false, dataType = "map", paramType = "query" ),
		@ApiImplicitParam(name="bookName", value="도서명", required = true, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="writer", value="저자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="publisher", value="출판사", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="buyer", value="구매자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="lender", value="대여자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="price", value="가격", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="state", value="상태(대여가능/대여중/알수없음)", required = false, dataType = "string", paramType = "query", defaultValue = "대여가능" ),
		@ApiImplicitParam(name="info", value="비고", required = false, dataType = "string", paramType = "query", defaultValue = "" )
	})
	@RequestMapping(value = "/book", produces="application/json; charset=utf8", method = RequestMethod.POST )
	public @ResponseBody Map<String,Object> bookRegist( @RequestBody Map<String, String> params ){
		log.info("[bookRegist] param:{}",params);
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			Book book = new Book( params );
			BookHistory bookHistory = new BookHistory( params );
			book = bookJpaRepository.save( book );
			bookHistory.setBookId( book.getId() );
			bookHistoryJpaRepository.save( bookHistory );
			
			result.put("RESULT", "저장완료");
		} catch (Exception e) {
			log.error("[bookRegist] Fail : {}",e);
			result.put("RESULT", "저장실패");
		}
		return result;
	}
	
	/** book modify  */
	@ApiOperation(value = "도서 수정", notes = "도서 수정 API")
	@ApiImplicitParams({
		@ApiImplicitParam(name="params", value="입력하지 않아도 되는 필드", required = false, dataType = "map", paramType = "query" ),
		@ApiImplicitParam(name="id", value="도서구분번호", required = true, dataType = "string", paramType = "query" ),
		@ApiImplicitParam(name="bookName", value="도서명", required = true, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="writer", value="저자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="publisher", value="출판사", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="buyer", value="구매자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="lender", value="대여자", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="price", value="가격", required = false, dataType = "string", paramType = "query", defaultValue = "" ),
		@ApiImplicitParam(name="state", value="상태(대여가능/대여중/알수없음)", required = false, dataType = "string", paramType = "query", defaultValue = "대여가능" ),
		@ApiImplicitParam(name="info", value="비고", required = false, dataType = "string", paramType = "query", defaultValue = "" )
	})
	@RequestMapping(value = "/book", produces="application/json; charset=utf8", method = RequestMethod.PUT )
	@CacheEvict(value = "book", key="#id")
	public @ResponseBody Map<String,Object> bookModify( @RequestBody Map<String, String> params, Long id ){
		log.info("[bookModify] param : {} / id : {}",params,id);
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			
			BookHistory bookHistory = new BookHistory( params );
			
			if ( id == 0 || StringUtils.isEmpty( id ) ) {
				result.put("RESULT", "수정실패");
				return result;
			}			
			Long cnt = dslRepository.updateBook( params );
			bookHistoryJpaRepository.save( bookHistory );
			
			result.put("RESULT", "["+cnt+"]건 수정성공");
			
		} catch (Exception e) {
			log.error("[bookModify] Fail : {}",e);
			result.put("RESULT", "수정실패");
		}

		
		return result;
	}
	
	/** book info  */
	@ApiOperation(value = "도서 상세 정보 조회", notes = "도서 상세 정보 조회 API")
	@ApiImplicitParam(name="id", value="도서구분번호", required = true, dataType = "long", paramType = "query" )
	@RequestMapping(value = "/book/{id}", produces="application/json; charset=utf8",  method = RequestMethod.GET )
	@Cacheable( value="book", key = "#id")
	public @ResponseBody Map<String,Object> bookInfo( @RequestParam Long id ){
		log.info("[bookInfo] id : {}",id);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", String.valueOf(id));
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			Book book = new Book( param );
		
			// Book 상세정보 조회
			Book info = dslRepository.findBookId( book.getId() );
			List<BookHistory> bookHistoryList = dslRepository.findBookHistory( book.getId() );

			result.put("INFO", info);
			result.put("HISTORY_LIST", bookHistoryList);
			result.put("RESULT", "조회성공");
		} catch (Exception e) {
			log.error("[bookInfo] Fail : {}",e);
			result.put("RESULT", "조회실패");
		}
		return result;
	}
	
	/** book del  */
	@ApiOperation(value = "도서 삭제", notes = "도서 삭제 API")
	@ApiImplicitParam(name="id", value="도서구분번호", required = true, dataType = "long", paramType = "query" )
	@RequestMapping(value = "/book/{id}", produces="application/json; charset=utf8", method = RequestMethod.DELETE )
	@CacheEvict( value = "book", key = "#id" )
	public @ResponseBody Map<String,Object> bookDel( @RequestParam Long id ){
		log.info("[bookDel] id : {}",id);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", String.valueOf(id));
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			Book book = new Book( param );
			if ( book.getId() == 0 || StringUtils.isEmpty( book.getId() ) ) {
				result.put("RESULT", "삭제실패");
				return result;
			}
			if( dslRepository.deleteId( book.getId() ) > 0 ) {
				bookHistoryJpaRepository.deleteBookHistory( book.getId() );
				
				result.put("RESULT", "삭제완료");
			} else {
				result.put("RESULT", "삭제실패");
			}
		} catch (Exception e) {
			log.error("[bookDel] Fail : {}",e);
			result.put("RESULT", "삭제실패");
		}
		return result;
	}
	
	public void calculate( Page<Book> list ) {
		this.endId = Math.min( (( list.getNumber()-1)/(this.pageBlockSize-1))*(this.pageBlockSize-1) + this.pageBlockSize,list.getTotalPages());
		this.startId = (((list.getNumber()-1)/(this.pageBlockSize-1))*(this.pageBlockSize-1))+1;
	}
	
}
