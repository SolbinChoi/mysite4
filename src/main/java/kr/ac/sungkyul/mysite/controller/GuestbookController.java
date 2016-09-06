package kr.ac.sungkyul.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.sungkyul.mysite.service.GuestbookService;
import kr.ac.sungkyul.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping("/list")
	public String list(Model model){
		List<GuestbookVo> list = guestbookService.list();
		model.addAttribute("list",list);
		return "/guestbook/list"; // 포워딩
	}
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestbookVo vo){
		guestbookService.insert(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping("/deleteform")
	public String deleteform(
			@RequestParam(value="no", required=false, defaultValue="-1") Long no, 
			Model model){
		model.addAttribute("no", no);
		return "/guestbook/deleteform"; // deleteform.jsp 포워딩
	}
	
	@RequestMapping(value ="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo){
		guestbookService.delete(vo);
		return "redirect:/guestbook/list";
	}
}
