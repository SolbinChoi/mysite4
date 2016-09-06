package kr.ac.sungkyul.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.sungkyul.mysite.service.BoardService;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	// 파라미터에서 required=true일때 page가 반드시 파라미터로 넘어가야 list를 확인하도록!
	@RequestMapping("/list")
	public String list(Model model,
			@RequestParam(value="p", required=true, defaultValue="1") Long page,
			@RequestParam(value="kwd", required=false, defaultValue="") String keyword){
		Map<String, Object> map = boardService.list(page, keyword);
		model.addAttribute("map",map);
		
		return "/board/list";
	}
	@RequestMapping("/view")
	public String view(Model model,
			@RequestParam(value="no", required=true, defaultValue="") Long no){
		BoardVo vo = boardService.view(no);
		if(vo==null){
			return "redirect:/board/list";
		}
		boardService.viewCount(no);
		model.addAttribute("vo", vo);
		System.out.println(vo);
		return "/board/view";
	}
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String writeform(HttpSession session){
		if(session == null){
			return "redirect:/main";
		}
		return "/board/write";
	}
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session,
			@ModelAttribute BoardVo vo){
		if(session == null){
			return "redirect:/main";
		}
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/main";
		}
		vo.setUserNo(authUser.getNo()); // vo에 유저번호를 붙여서 보내 아니면 JSP 페이지에 &userno=${authUser.no}가능
		boardService.write(vo);
		return "redirect:/board/list";
	}
	@RequestMapping("/delete")
	public String delete(@ModelAttribute BoardVo vo){
		boardService.delete(vo);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/modifyform")
	public String modifyfrom(Model model
			, @RequestParam(value="no", required=true, defaultValue="") Long no){
		// 번호에 해당되는 title, content를 가져와
		BoardVo vo = boardService.boardInfo(no);
		model.addAttribute("vo",vo);
		return "/board/modify";
	}
	@RequestMapping("/modify")
	public String modify(HttpSession session,
			@ModelAttribute BoardVo vo){
		boardService.modify(vo);
		return "redirect:/board/list";
	}
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String replyform( Model model, @RequestParam("no") Long no){
	
		BoardVo vo = boardService.boardInfo(no);
		model.addAttribute("vo",vo);
		System.out.println(vo);
		return "/board/reply"; // reply.jsp 포워딩
	}
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(HttpSession session,
				@ModelAttribute BoardVo vo){
		int groupOrderNo = vo.getGroupOrderNo() + 1;
		int depth = vo.getDepth() + 1;

		vo.setGroupOrderNo(groupOrderNo);
		vo.setDepth(depth);
		
		boardService.updateOrderNo(vo);
		boardService.write(vo);
		System.out.println(vo);
		return "redirect:/board/list";
	}
	@RequestMapping("/right")
	public String right(){
		return "/board/right";
	}
}
