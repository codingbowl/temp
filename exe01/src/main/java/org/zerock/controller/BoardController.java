package org.zerock.controller;



import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	/*GET방식은 항상 사용자가 직접 브라우저에서 접근이 가능할 때 사용한다.
	1.입력 페이지
	2.조회 페이지*/
	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get .....................");
	}
	
	/*POST방식은 항상 외부에서 많은 정보를 입력하는 경우에 사용한다.
	브라우저상에서 주소창에 보여지는 안 되는 정보를 전송하는 데 처리한다.*/
	@RequestMapping(value = "/register" ,method = RequestMethod.POST)
	public String registPOST(BoardVO board, Model model) throws Exception {
		logger.info("regist post ........................");
		logger.info(board.toString());
		
		service.regist(board);
		
		model.addAttribute("result", "success");
		
		return "/board/success";
	}
}
