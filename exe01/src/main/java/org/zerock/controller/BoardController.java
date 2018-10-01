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
	 * GET방식은 항상 사용자가 직접 브라우저에서 접근이 가능할 때 사용한다. 1.입력 페이지 2.조회 페이지
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get .....................");
	}

	/*
	 * POST방식은 항상 외부에서 많은 정보를 입력하는 경우에 사용한다. 브라우저상에서 주소창에 보여지는 안 되는 정보를 전송하는 데 처리한다.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("regist post ........................");
		logger.info(board.toString());

		service.regist(board);

		/*
		 * result-success 문자열이 지워지지 않고 남기 때문에 RedirectAttributes 를 사용한다.
		 * RedirectAttributes 객체는 다이렉트 시점에 한 번만 사용되는 데이터를 전송할 수 있는 addFlastAttribute()
		 * 라는 기능을 지원한다.
		 * 
		 * 이후 추가적인 문자열이 없어진 것을 확인 할 수 있다.
		 */
		// model.addAttribute("result", "success");
		rttr.addFlashAttribute("msg", "success");

		/* 새로 고침과 계속을 선택하게 되면 도배가 발생 이러한 문제를 막기 위해 리다이렉트를 이용한다. */
		// return "/board/success";

		return "redirect:/board/listAll";
	}

	/* 게시글 전체조회 */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception {
		logger.info("show all list .........................");
		model.addAttribute("list", service.listAll());
	}

	/* 게시글 상세페이지 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model) throws Exception {
		/*
		 * RequestParam 애노테이션은 Servlet에서 request.getParameter()의 효고와 유사하다. Servlet의
		 * HttpServletRequest와 다른점은 문자열, 숫자, 날짜 등의 형 변환이 가능하다는 점 Model addAtribute() 작업을
		 * 할때 아무런 이름 없이 데이터를 넣으면 자동으로 클래스의 이름을 소줌나졸 시작해서 사용하게 된다. 즉 BoardVO 클래스의 객체이므로,
		 * bardVO라는 이름으로 저장하게 된다.
		 */
		model.addAttribute(service.read(bno));
	}

	/* 게시글 삭제 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addFlashAttribute("msg", "remove success");
		
		return "redirect:/board/listAll";
	}

	/* 게시글 수정 - 상세페이지 */
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}

	/* 게시글 수정 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(BoardVO boardVO, RedirectAttributes rttr) throws Exception {
		logger.info("mod post ................................");
		service.modify(boardVO);
		rttr.addFlashAttribute("msg", "modify success");

		return "redirect:/board/listAll";
	}
	
	/*게시물 전체조회 페이징*/
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
