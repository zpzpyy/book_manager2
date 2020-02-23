<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Enliple 도서 관리 페이지</title>

	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/custom.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/bootstrap.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/jquery-ui.min.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/jquery.mCustomScrollbar.css"/>">
	
	<script src="<c:url value="/resource/js/jquery-3.4.1.js"/>"></script>
	<script src="<c:url value="/resource/js/jquery-ui.min.js"/>"></script>
	<script src="<c:url value="/resource/js/custom.js"/>"></script>
	<script src="<c:url value="/resource/js/popper.min.js"/>"></script>
	<script src="<c:url value="/resource/js/bootstrap.js"/>"></script>
	<script src="<c:url value="/resource/js/jquery.mCustomScrollbar.js"/>"></script>
</head>

<body>

<div style="margin: 10px;">
	<button type="button" class="btn" onclick="this.blur();" id="mainBtn" title="Main Page">
		<span style="font-size: 70px;">E</span>
		<span style="font-size: 25px;">nliple
			<span style="font-size: 70px;">B</span>ook Manager
			<span style="font-size: 10px;">@author ljs</span>
		</span>
	</button>
</div>

<div>
	<div class="row">

		<input type="hidden" id="sortKey" name="sortKey" value="${sortKey}">
		<input type="hidden" id="sortValue" name="sortValue" value="${sortValue}">
	
		<div class="col-sm" style="margin-left: 20px;">
			<button type="button" class="btn btn-success" data-target="#bookRegistModal" data-toggle="modal">도서등록</button>
		</div>
		<div class="col-sm-4" style="margin-right: 20px;">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
			    <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="searchKeyBtn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">조회조건</button>
			    <input type="hidden" id="searchKey" name="searchKey" value="${searchKey}">
			    <div class="dropdown-menu">
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('bookName', this);">도서명</a>
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('writer', this);">저자</a>
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('publisher', this);">출판사</a>
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('buyer', this);">구매자</a>
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('lender', this);">대여자</a>
			      <a class="dropdown-item" href="#;return false;" onclick="exchangeSearchType('state', this);">상태</a>
			    </div>
			  </div>
			  <input type="text" id="searchValue" name="searchValue" value="${searchValue}" class="form-control">
			  <button type="button" class="btn btn-primary" onclick="search();">검색</button>
			</div>
		</div>
		
	</div>
</div>

<div style=" margin-top: 10px;" class="col-sm-12">
	<table class="table table-hover">
		<colgroup>
			<col width="5%">
			<col width="20%">
			<col width="15%">
			<col width="10%">
			<col width="8%">
			<col width="8%">
			<col width="9%">
			<col width="5%">
			<col width="10%">
			<col width="10%">	
		</colgroup>
		<thead class="thead-dark">
			<tr style="text-align: center;">
				<th scope="col"><a href="#;return false;" class="b_columns" c-name="id" id="c_id">SEQ</a></th>
				<th scope="col"><a href="#;return false;" class="b_columns " c-name="bookName" id="c_bookName">도서명</a></th>
				<th scope="col">저자</th>
				<th scope="col">출판사</th>
				<th scope="col">구매자</th>
				<th scope="col">대여자</th>
				<th scope="col"><a href="#;return false;" class="b_columns" c-name="state" id="c_state">상태</a></th>
				<th scope="col">가격</th>
				<th scope="col"><a href="#;return false;" class="b_columns" c-name="uptDate" id="c_uptDate">수정일</a></th>
				<th scope="col"><a href="#;return false;" class="b_columns" c-name="regDate" id="c_regDate">등록일</a></th>
			</tr>
		</thead>
		<tbody id="listBody" style="text-align: center;">
		
		</tbody>
	</table>
</div>

<div style="margin: 20px;" align="center">
	<ul class="pagination right" id="pagination" >
	</ul>
</div>

<form id="frm" name="frm" role="form" class="form-group">
	<input type="hidden" id="maxPage" name="maxPage">
</form>


<!-- 등록 Modal -->
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" id="bookRegistModal">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="bookRegistModal-title">도서 정보 입력</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        
      </div>
      
      <form id="bookRegistForm" class="form-horizontal" role="form">
	       <div class="modal-body">
	      	
	      	<input type="hidden" id="id" name="id">
	      	<input type="hidden" id="beforeOrderKey" name="beforeOrderKey">
	      	<input type="hidden" id="beforeOrderValue" name="beforeOrderValue">

      		<!-- 도서명 -->
      		<div class="row">
				<div class="input-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><font style="color: red; margin-right: 5px;">* </font><b>도서명</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="bookName" name="bookName" maxlength="50" aria-describedby="basic-addon3">
				</div>
			</div>
			<!-- 저자 -->
			<div class="row">
				<div class="input-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>저자</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="writer" name="writer" maxlength="50" aria-describedby="basic-addon3">
				</div>
			</div>
			<!-- 출판사 -->
			<div class="row">
				<div class="input-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>출판사</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="publisher" name="publisher" maxlength="13" aria-describedby="basic-addon3">
				</div>
			</div>
			
			<div class="row">
			  	<!-- 구매자 -->
				<div class="input-group col-lg-6 col-md-6 col-sm-6 col-xs-6" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>구매자</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="buyer" name="buyer" maxlength="7" aria-describedby="basic-addon3">
				</div>
			  	<!-- 대여자 -->
				<div class="input-group col-lg-6 col-md-6 col-sm-6 col-xs-6" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>대여자</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="lender" name="lender" maxlength="7" aria-describedby="basic-addon3">
				</div>
			</div>
			
			<div class="row">
				<!-- 가격 -->
				<div class="input-group col-lg-6 col-md-6 col-sm-6 col-xs-6" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>가격</b></span>
				 	</div>
				  	<input type="text" class="form-control inputColumn" id="price" name="price" maxlength="6" aria-describedby="basic-addon3">
				</div>
				<!-- 상태 -->
				<div class="input-group col-lg-6 col-md-6 col-sm-6 col-xs-6" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>상태</b></span>
				 	</div>
				  	<select id="state" name="state" class="form-control inputColumn">
				  		<option>대여가능</option>
				  		<option>대여중</option>
				  		<option>알수없음</option>
				  	</select>
				</div>
			</div>
			
			
			<!-- 기타(비고) -->
			<div class="row">
				<div class="input-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: 20px;">
					<div class="input-group-prepend">
				    	<span class="input-group-text" id="basic-addon3"><b>기타</b></span>
				 	</div>
				  	<textarea rows="4" id="info" name="info" class="form-control col-sm-12 inputColumn"></textarea>
				</div>
			</div>
			
			<div class="row">
				<div class="input-group fixed-table-body col-lg-12 col-md-12 col-sm-12 col-xs-12 content" id="infoDiv" style="display: none; height: 200px;">
					<table class="table col-lg-12 col-md-12 col-sm-12 col-xs-12" id="infoDivTable" style="width: 735px;">
						<colgroup>
							<col width="25%">
							<col width="25%">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<thead>
							<tr style="text-align: center; border: 1px solid grey; height: 30px;">
								<th scope="col">일자</th>
								<th scope="col">구매자</th>
								<th scope="col">대여자</th>
								<th scope="col">상태</th>
							</tr>
						</thead>
						<tbody id="infoBody" style="text-align: center;">
						</tbody>
					</table>
				</div>
		  	</div>
		  	
	      </div>
	      
	      <div class="modal-footer">
          		<button type="button" id="saveBtn" class="btn btn-primary" onclick="bookRegist();">저장</button>
          		<button type="button" id="modifyBtn" class="btn btn-warning" style="display: none;">수정</button>
          		<button type="button" id="modifyBtnExcute" class="btn btn-warning" onclick="bookModify();" style="display: none;">수정</button>
          		<button type="button" id="delBtn" class="btn btn-danger" style="display: none;">삭제</button>
        		<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      	  </div>
      </form>
     
    </div>
  </div>
</div>



<script type="text/javascript">
var defaultSize = 10;

window.onload = function (){
    if (window.Notification){
        Notification.requestPermission();
    }
}

$(window).resize(function(){
	var width = $(window).width();
	if ( width <= 990){
		$("#infoDivTable").css("width","435px");
	} else {
		$("#infoDivTable").css("width","735px");
	}
});

$(document).ready(function(){

	init();
	
	getList('<c:url value="/api/book"/>' , '0');
	
	// 메인 버튼 클릭 이벤트 
	$("#mainBtn").on("click", function(){
		$("#searchKey").val("");
		$("#searchValue").val("");
		$("#sortKey").val("id");
		$("#sortValue").val("ASC");
		var beforeStrSplit= $("#"+$("#beforeOrderKey").val()).html().split(" ", 2);
		$("#"+$("#beforeOrderKey").val()).html( beforeStrSplit[0] );
		init();
		
		getList('<c:url value="/api/book"/>' , '0');
	});
	
	// 모달창 닫기 이벤트
	$("#bookRegistModal").on('hidden.bs.modal', function () {
		
		$("#bookRegistForm .inputColumn").attr("readonly",false);
		$("#bookRegistForm #state").attr("disabled", false);
		
		$("#bookRegistForm #id").val("");
		$("#bookRegistForm #bookName").val("");
		$("#bookRegistForm #writer").val("");
		$("#bookRegistForm #publisher").val("");
		$("#bookRegistForm #buyer").val("");
		$("#bookRegistForm #lender").val("");
		$("#bookRegistForm #price").val("");
		$("#bookRegistForm #state").val("대여가능");
		$("#bookRegistForm #info").val("");
		
		$("#bookRegistForm #saveBtn").show();
		$("#bookRegistForm #modifyBtn").hide();
		$("#bookRegistForm #modifyBtnExcute").hide();
		$("#bookRegistForm #delBtn").hide();
		$("#bookRegistForm #infoDiv").hide();
		
		$("#bookRegistModal #bookRegistModal-title").html("도서 정보 입력");
	});
	
	// 수정버튼 클릭 이벤트 
	$("#modifyBtn").on("click", function(){
		$("#bookRegistForm .inputColumn").attr("readonly",false);
		$("#bookRegistForm #state").attr("disabled", false);
		$("#bookRegistModal #bookRegistModal-title").html("도서 정보 수정");
		$("#bookRegistForm #modifyBtn").hide();
		$("#bookRegistForm #modifyBtnExcute").show();
	});
	
	// 삭제버튼 클릭 이벤트 
	$("#delBtn").on("click", function(){
		bookDel();
	});
	
	// 조회컬럼에서 키이벤트처리
	$("input[name=searchValue]").keydown(function (key) {
        if(key.keyCode == 13){
            search();
        }
    });
	
	// 정렬 이벤트 처리
	$(".b_columns").on("click", function( ){
		$("#sortKey").val( $(this).attr("c-name") );
		var strSplit= $(this).html().split(" ", 2);
		
		// 이전에 정렬했던 컬럼과 현재 정렬요청온 컬럼이 다르면 -> 이전 컬럼 삼각형 제거
		if ( ($("#beforeOrderValue").val() != strSplit[0]) &&  $("#beforeOrderValue").val() != "" ){
			var beforeStrSplit= $("#"+$("#beforeOrderKey").val()).html().split(" ", 2);
			$("#"+$("#beforeOrderKey").val()).html( beforeStrSplit[0] );
		}

		// 오름차순 -> 내림차순
		if ( $("#sortValue").val() == "ASC" ){
			$(this).html( strSplit[0]+ " ▽" );			
			$("#sortValue").val("DESC");
		// 내림차순 -> 오름차순
		} else {
			var strSplit= $(this).html().split(" ", 2);
			$(this).html( strSplit[0]+ " △" );
			$("#sortValue").val("ASC");
		}
		
		$("#beforeOrderKey").val( $(this).attr("id") );
		$("#beforeOrderValue").val(strSplit[0]);
		
		getList('<c:url value="/api/book"/>' , '0');
	});
	
	
});

function init(){
	$("#c_id").html( "SEQ △" );
	$("#beforeOrderKey").val( "c_id" );
	$("#beforeOrderValue").val( "id" );
	
	$(".content").mCustomScrollbar({
		theme: "dark"
	});
}

// Book list 요청
function getList( url, pageNum ){
	var searchKey = $("#searchKey").val();
	var searchValue = $("#searchValue").val();
	var sortKey = $("#sortKey").val() == "" ? "id" : $("#sortKey").val();
	var sortValue = $("#sortValue").val() == "" ? "ASC" : $("#sortValue").val() ;
	
	var url = url +"?" + $("#frm").serialize() + "&page=" + pageNum + "&size=" + defaultSize + "&searchKey=" + searchKey + "&searchValue=" + searchValue + "&sortKey=" + sortKey + "&sortValue=" + sortValue
	
//	var dataJson = { "maxPage" 		: 	$("#maxPage").val(),
//				 	 "page"    		: 	pageNum,
//				 	 "size"    		: 	defaultSize,
//				 	 "searchKey"	:	searchKey,
//				 	 "searchValue"	: 	searchValue,
//				 	 "sortKey"		:	sortKey,
//				 	 "sortValue"	:	sortValue
//				   };
	
	$.ajax({
		url: url,
		type: 'GET',
//		data: JSON.stringify( dataJson ),
		contentType : 'application/json; charset=utf8',
		dataType: 'json',
		success: function( data ){
			drawBookTable( data.paging.content );
			drawPager( data );
			putSort( data );
		},		
		error: function(request, statis, error){
			 alert("Error\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		//	 push("Book List Road Error", "BookList 정보를 불러오는 중 에러가 발생했습니다.", "");
		}
		
	});
}

// Book 정보 등록(저장)
function bookRegist(){
	//var dataString = $("#bookRegistForm").serialize();
	
	var dataJson = {	"bookName" 	: 	$("#bookRegistForm #bookName").val(),
						"writer"	: 	$("#bookRegistForm #writer").val(),
						"publisher"	:	$("#bookRegistForm #publisher").val(),
						"buyer"		:	$("#bookRegistForm #buyer").val(),
						"lender"	:	$("#bookRegistForm #lender").val(),
						"price"		:	$("#bookRegistForm #price").val(),
						"state"		:	$("#bookRegistForm #state").val(),
						"info"		:	$("#bookRegistForm #info").val()
					 };	

	
	if( validation() ){
		
		$.ajax({
			url: '<c:url value="/api/book"/>',
			type: 'POST',
			data: JSON.stringify( dataJson ),
			contentType : 'application/json; charset=utf8',
			dataType: 'json',
			success: function( data ){
			//	push("Book Regist", "Book 정보가 저장되었습니다.", "");
				alert( data.RESULT );
				$("#bookRegistModal").modal("hide");
				getList('<c:url value="/api/book"/>' ,'0');
			},
			error: function(request, statis, error){
				 alert("Error\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			//	 push("Book Regist Error", "Book 정보 저장 중 에러가 발생했습니다.", "");
			}
		});	
	} else {
		alert("유효성 에러");
	}
}

// 유효성체크
function validation(){
	if ( $.trim($("#bookName").val())=="" || $.trim($("#bookName").val()).length==0 ){
		alert("도서명은 필수입력 사항입니다.");
		$("#bookName").val("")
		$("#bookName").focus();
		return false;
	} else {
		return true;
	}
}

// Book 수정요청
function bookModify(){
//	var dataString = $("#bookRegistForm").serialize();
	
	var dataJson = {	"id"		:   $("#bookRegistForm #id").val(),
						"bookName" 	: 	$("#bookRegistForm #bookName").val(),
						"writer"	: 	$("#bookRegistForm #writer").val(),
						"publisher"	:	$("#bookRegistForm #publisher").val(),
						"buyer"		:	$("#bookRegistForm #buyer").val(),
						"lender"	:	$("#bookRegistForm #lender").val(),
						"price"		:	$("#bookRegistForm #price").val(),
						"state"		:	$("#bookRegistForm #state").val(),
						"info"		:	$("#bookRegistForm #info").val()
		 		   };
	
	$.ajax({
		url: '<c:url value="/api/book"/>?id='+$("#bookRegistForm #id").val(),
		type: 'PUT',
		data: JSON.stringify( dataJson ),
		contentType : 'application/json; charset=utf8',
		dataType: 'json',
		success: function( data ){
			if ( data.RESULT == "수정실패" ){
			//	push("Book Modify", "Book 정보 수정이 실패 되었습니다.", "");
			} else {
			//	push("Book Modify", "Book 정보가 수정 되었습니다.", "");
			}
			alert( data.RESULT );
			$("#bookRegistModal").modal("hide");
			getList('<c:url value="/api/book"/>' , '0');
		},
		error: function(request, statis, error){
			alert("Error\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		//	push("Book Modify Error", "Book 정보 수정 중 에러가 발생했습니다.", "");
		}
	});

}

// Book 삭제
function bookDel(){
	var id = $("#bookRegistForm #id").val();

	if( confirm("도서명 ["+$("#bookName").val() + "] 를 삭제 하시겠습니까?") ){
		$.ajax({
			url: '<c:url value="/api/book/'+ id +'"/>?id='+id,
			type: 'DELETE',
//			data: JSON.stringify( dataJson ),
//			data : dataJson,
			contentType : 'application/json; charset=utf8',
			dataType: 'json',
			success: function( data ){
			//	push("Book Delete", "[" + $("#bookName").val() + "] 정보가 삭제 되었습니다.", "");
				alert( data.RESULT );
				$("#bookRegistModal").modal("hide");
				getList('<c:url value="/api/book"/>' , '0');
			},
			error: function(request, statis, error){
				alert("Error\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			//	push("Book Delete Error", "Book 정보 삭제 중 에러가 발생했습니다.", "");
			}
		});	
	} 
	
}

// 조회리스트 가져와서 정렬값 세팅
function putSort( data ){
	$("#sortKey").val( data.sortKey );
	$("#sortValue").val( data.sortValue );
}

// 조회조건 변경 이벤트처리
function exchangeSearchType( searchKey, e ){
	$("#searchKey").val( searchKey );
	$("#searchKeyBtn").html( $(e).html() );
}

// Book search 조회
function search(){
	
	if ( $("#searchKey").val() == "" || $("#searchKey").val() == null ){
		alert("조회조건을 선택해 주세요");
		$("#searchKeyBtn").focus();
		return;
	}
	getList('<c:url value="/api/book"/>', '0');
}

// Book 리스트 생성
function drawBookTable( data ){
	$("#listBody").html("");
	var html = "";
	
	if ( data == null || data.length == 0 ){
		html = '<tr style="font-size:13px;"><td colspan="10" align="center"><img src="<c:url value="/resource/image/noData.png"/>"></td></tr>';
	} else {

		$.each( data, function(index, list){
			
			var regDate = new Date(list.regDate).format("yyyy-MM-dd HH:mm:ss");
			var uptDate = new Date(list.uptDate).format("yyyy-MM-dd HH:mm:ss");
			
			var c_state = "alert-primary";
			if ( list.state == '대여가능' ) {
				c_state = "alert-primary";
 			} else if ( list.state == '대여중' ) {
 				c_state = "alert-success";
 			} else {
 				c_state = "alert-danger";
 			}
			
			html += '<tr style="font-size:12px;">' +
 						'<th scope="row">'+list.id+'</th>' +
						'<td><a href="#;return false;" onclick=\"bookInfo('+list.id+')\">'+list.bookName+'</a></td>' +
		     			'<td>'+isEmpty(list.writer)+'</td>' +
		     			'<td>'+isEmpty(list.publisher)+'</td>' +
		     			'<td>'+isEmpty(list.buyer)+'</td>' +
		     			'<td>'+isEmpty(list.lender)+'</td>' +
		     			'<td>' +
		     				'<div class="alert-xs '+c_state+'" role="alert"><b>' +
		     				 	list.state +
		     				'</b></div>' +
		     			'</td>' +
		     			'<td>'+numberWithCommas(isEmpty(list.price))+'</td>' +
		     			'<td>'+isEmpty(uptDate)+'</td>' +
		     			'<td>'+ regDate +'</td>' +
		     		'</tr>'
		});
	}
	$("#listBody").append( html );
}

// book 정보 가져오기
function bookInfo( id ){
	var dataJson = { "id" : id }
	
	$.ajax({
		url: '<c:url value="/api/book/'+id+'"/>?id='+id,
		type: 'GET',
		contentType : 'application/json',
		dataType: 'json',
		success: function( data ){
			if( data.RESULT == "조회성공"){
				drawBookInfo( data );
			} else {
				alert( data.RESULT );	
			}
		},
		error: function(request, statis, error){
			 alert("Error\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		//	 push("Book Info Road Error", "Book 정보를 가져오는 중 에러가 발생했습니다.", "");
		}
	});
	
}

// book 정보 Set
function drawBookInfo( data ){
	
	$("#infoBody").html("");
	var html = "";
	
	var info = data.INFO;
	var historyList = data.HISTORY_LIST;
	
	if( info == null || info.length == 0 ){
		alert("도서 정보가 존재하지 않습니다.");
		return;
	}
	
	$("#bookRegistForm .inputColumn").attr("readonly","readonly");
	$("#bookRegistForm #state").attr("disabled", true);
	$("#bookRegistForm #infoDiv").attr("disabled", true);
	
	$("#bookRegistForm #id").val(info.id);
	$("#bookRegistForm #bookName").val(info.bookName);
	$("#bookRegistForm #writer").val(info.writer);
	$("#bookRegistForm #publisher").val(info.publisher);
	$("#bookRegistForm #buyer").val(info.buyer);
	$("#bookRegistForm #lender").val(info.lender);
	$("#bookRegistForm #price").val(info.price);
	$("#bookRegistForm #state").val(info.state);
	$("#bookRegistForm #info").val(info.info);
	
	if ( historyList == null || historyList.length == 0 ){
		html = '<tr style="font-size:13px;"><td colspan="4" align="center">History 데이터가 존재 하지 않습니다.</td></tr>';
		$("#infoDiv").css("height","0px");
	//	push("도서 정보 확인", "["+info.bookName+"] 의 History 데이터가 존재 하지 않습니다.", "");
	} else {
		$("#infoDiv").css("height","200px");
		$.each( historyList, function(index, list){
			var regDate = new Date(list.regDate).format("yyyy-MM-dd HH:mm:ss");
			var c_state = "alert-primary";
			if ( list.state == '대여가능' ) {
				c_state = "alert-primary";
				} else if ( list.state == '대여중' ) {
					c_state = "alert-success";
				} else {
					c_state = "alert-danger";
				}
			
			html += '<tr style="font-size:12px;">' +
						'<td scope="row">'+regDate+'</td>' +
						'<td>'+isEmpty(list.buyer)+'</td>' +
						'<td>'+isEmpty(list.lender)+'</td>' +
						'<td>' +
							'<div class="alert-xs '+c_state+'" role="alert"><b>' +
							 	list.state +
							'</b></div>' +
						'</td>' +
					'</tr>'
		});
	}
	
	$("#infoBody").append( html );
	
	$("#bookRegistForm #saveBtn").hide();
	$("#bookRegistForm #modifyBtn").show();
	$("#bookRegistForm #delBtn").show();
	$("#bookRegistForm #infoDiv").show();
	
	$("#bookRegistModal #bookRegistModal-title").html("도서 정보 확인");

	$("#bookRegistModal").modal("show");
}

// 페이지 생성
function drawPager( data ){
	var paging = data.paging;
	var page = paging.pageable;
	var listHtml = "";

	var maxPage = paging.totalPages-1;
	var size = paging.size;
	var prevPage = data.currentId-1==0? 0 : data.currentId-1;
	var nextPage = data.endId-1==maxPage ? maxPage : data.currentId+1;
	
	listHtml += '<li><a href="javascript:;" onclick="movePage('+0+');">&lt;&lt;</a></li>';
	listHtml += '<li><a href="javascript:;" onclick="movePage('+"'"+prevPage+"'"+');">&lt;</a></li>';
	
	for(var i = data.startId; i <= data.endId ;  i++) {
		if( data.currentId == parseInt(i) ){
			listHtml +='<li class="active"><a href="javascript:;" onclick="movePage('+"'"+i+"'"+');">'+(parseInt(i)+1)+"</a></li>";
		} else {
			listHtml +='<li><a href="javascript:;" onclick="movePage('+"'"+i+"'"+');">'+(parseInt(i)+1)+"</a></li>";
		}
	}
	
	listHtml+='<li><a href="javascript:;" onclick="movePage('+"'"+nextPage+"'"+');">&gt;</a></li>';
	listHtml+='<li><a href="javascript:;" onclick="movePage('+"'"+maxPage+"'"+');"">&gt;&gt;</a></li>';
	
	$("#pagination").html( listHtml );
	$("#maxPage").val( maxPage );
	
}

// 페이지이동
function movePage( page ) {
	var maxPage = $("#maxPage").val();
	if ( parseInt( page ) >= parseInt( maxPage ) ){
		page = maxPage;
	}
	
	if ( page < 0 ){
		page = 0;
	}
	
	getList('<c:url value="/api/book"/>' , page );
}

</script>

</body>
</html>