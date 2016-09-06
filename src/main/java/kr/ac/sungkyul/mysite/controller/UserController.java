package kr.ac.sungkyul.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.sungkyul.mysite.service.UserService;
import kr.ac.sungkyul.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/joinform")
	public String joinform(){
		return "user/joinform";
	}
	
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo vo){
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(){
		return "user/joinsuccess"; // 포워딩
	}
	
	@RequestMapping("/loginform")
	public String loginform(){
		return "user/loginform";
	}
	
	 @RequestMapping(value="/login", method=RequestMethod.POST)
	   public String login(
		  HttpSession session,
	      @RequestParam(value="email", required=false, defaultValue="") String email,
	      @RequestParam(value="password", required=false, defaultValue="") String password){
	      UserVo authUser = userService.login(email,password);
	      if(authUser == null){
	    	  return "redirect:/user/loginform";
	      }
	      // 인증성공
	      session.setAttribute("authUser", authUser);
	      return "redirect:/main";
	   }
	 @RequestMapping("/logout")
	 public String logout(HttpSession session){ //HttpSession은 세션이 시작되는 순간부터 종료될 때 까지 데이터가 바인딩된다.
		 session.removeAttribute("authUser"); // 제거
		 // 제이세션아이디를 바꾸어주어야함
		 session.invalidate();
		 return "redirect:/main";
	 }
	 @RequestMapping("/modifyform")
	 public String modifyform( HttpSession session, Model model){
		 if(session == null){
			 return "redirect:/main";
		 }
		 UserVo authUser = (UserVo)session.getAttribute("authUser");
		 if(authUser==null){
			 return "redirect:/main";
		 }
		 Long no = authUser.getNo(); // 사용자의 번호를 가지고 dao에 접근하여 이름과 이메일을 얻어온다.
		 UserVo vo = userService.modifyInfo(no);
		 model.addAttribute("userVo", vo); // vo를 jsp로 넘겨줌
		 return "/user/modifyform";
	 }
	 @RequestMapping(value = "/modify", method=RequestMethod.POST)
	 public String modifyInfo(@ModelAttribute UserVo vo, HttpSession session){ // input된 vo를 받음
		 if(session == null) {
			 return "redirect:/main";
		 }
		 UserVo authUser = (UserVo) session.getAttribute("authUser");
		 if(authUser==null){
			 return "redirect:/main";
		 }
		 Long no=authUser.getNo();
	      vo.setNo(no);
		 userService.modify(vo); 
		 authUser.setName(vo.getName()); // 수정 후 user의 이름을 변경
		 return "redirect:/user/modifyform?r=success";
	 }
	 
}
