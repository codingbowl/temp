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
	
	/*GET����� �׻� ����ڰ� ���� ���������� ������ ������ �� ����Ѵ�.
	1.�Է� ������
	2.��ȸ ������*/
	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get .....................");
	}
	
	/*POST����� �׻� �ܺο��� ���� ������ �Է��ϴ� ��쿡 ����Ѵ�.
	�������󿡼� �ּ�â�� �������� �� �Ǵ� ������ �����ϴ� �� ó���Ѵ�.*/
	@RequestMapping(value = "/register" ,method = RequestMethod.POST)
	public String registPOST(BoardVO board, Model model) throws Exception {
		logger.info("regist post ........................");
		logger.info(board.toString());
		
		service.regist(board);
		
		model.addAttribute("result", "success");
		
		return "/board/success";
	}
}
