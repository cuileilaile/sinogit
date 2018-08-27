package com.sino.zhiyun;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.CommonUtils;

@Controller
@RequestMapping(value="/alarm")
public class AlarmEvent {

	@RequestMapping(value="/getModel.do")
	@ResponseBody 
	public String main(){
		String token=CommonUtils.getToken();
		Map<String, Object> authHeaders = new HashMap<>();
        authHeaders.put("apikey",CommonUtils.apikey);
        authHeaders.put("Cookie","X-Token="+token);
        String resp = CommonUtils.get("http://gw.test.coc.tencent.com/zhiyunCmdb/backend/getModule?operator=admin&fields=id,name&sort=id|desc&include=parent&mode=page&page=1&limit=10&appid=0",authHeaders);
        System.err.println(resp);
        return resp;
	}
	  
}
