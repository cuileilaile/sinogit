package com.sino.alarm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/alarm")
public class HelloAction {

	@Value("${apikey}")
	private  String hello;
	
	
	@RequestMapping(value="/hello.do")
	@ResponseBody 
	public String main(){
		System.out.println(hello);
		
		
		
		return hello;
	}
	
}
