package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;

	/*
	 * GET����� �׻� ����ڰ� ���� ���������� ������ ������ �� ����Ѵ�. 1.�Է� ������ 2.��ȸ ������
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get .....................");
	}

	/*
	 * POST����� �׻� �ܺο��� ���� ������ �Է��ϴ� ��쿡 ����Ѵ�. �������󿡼� �ּ�â�� �������� �� �Ǵ� ������ �����ϴ� �� ó���Ѵ�.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("regist post ........................");
		logger.info(board.toString());

		service.regist(board);

		/*
		 * result-success ���ڿ��� �������� �ʰ� ���� ������ RedirectAttributes �� ����Ѵ�.
		 * RedirectAttributes ��ü�� ���̷�Ʈ ������ �� ���� ���Ǵ� �����͸� ������ �� �ִ� addFlastAttribute()
		 * ��� ����� �����Ѵ�.
		 * 
		 * ���� �߰����� ���ڿ��� ������ ���� Ȯ�� �� �� �ִ�.
		 */
		// model.addAttribute("result", "success");
		rttr.addFlashAttribute("msg", "success");

		/* ���� ��ħ�� ����� �����ϰ� �Ǹ� ���谡 �߻� �̷��� ������ ���� ���� �����̷�Ʈ�� �̿��Ѵ�. */
		// return "/board/success";

		return "redirect:/board/listAll";
	}

	/* �Խñ� ��ü��ȸ */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception {
		logger.info("show all list .........................");
		model.addAttribute("list", service.listAll());
	}

	/* �Խñ� �������� */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model) throws Exception {
		/*
		 * RequestParam �ֳ����̼��� Servlet���� request.getParameter()�� ȿ��� �����ϴ�. Servlet��
		 * HttpServletRequest�� �ٸ����� ���ڿ�, ����, ��¥ ���� �� ��ȯ�� �����ϴٴ� �� Model addAtribute() �۾���
		 * �Ҷ� �ƹ��� �̸� ���� �����͸� ������ �ڵ����� Ŭ������ �̸��� ���ܳ��� �����ؼ� ����ϰ� �ȴ�. �� BoardVO Ŭ������ ��ü�̹Ƿ�,
		 * bardVO��� �̸����� �����ϰ� �ȴ�.
		 */
		model.addAttribute(service.read(bno));
	}

	/* �Խñ� ���� */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addFlashAttribute("msg", "remove success");
		
		return "redirect:/board/listAll";
	}

	/* �Խñ� ���� - �������� */
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}

	/* �Խñ� ���� */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(BoardVO boardVO, RedirectAttributes rttr) throws Exception {
		logger.info("mod post ................................");
		service.modify(boardVO);
		rttr.addFlashAttribute("msg", "modify success");

		return "redirect:/board/listAll";
	}
	
	/*�Խù� ��ü��ȸ ����¡*/
	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listAll(Criteria cri, Model model) throws Exception {
		logger.info("show list page with Criteria");
		
		model.addAttribute("list", service.listCriteria(cri));
	}
	
	public void listPage(Criteria cri, Model model) throws Exception {
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(131);
		
		model.addAttribute("pageMaker", pageMaker);
	}

}
