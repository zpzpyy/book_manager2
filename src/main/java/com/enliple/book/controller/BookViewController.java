package com.enliple.book.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookViewController {

	/** book index */
	@ApiOperation(value = "도서 메인 페이지 호출")
	@RequestMapping( value = "/book", method = RequestMethod.GET )
	public  String book( @RequestParam Map<String, String> param, Model model, Pageable pageable  ){
		log.info("[book] param : {}",param);
		return "index";
	}
	
}
