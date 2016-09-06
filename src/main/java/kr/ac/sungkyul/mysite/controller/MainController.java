package kr.ac.sungkyul.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/main")
	public String main(){
		
		return "main/index"; // 포워딩 (뷰리졸버를  사용했으니 생략)
	}
}
