package com.nobodyknows.learning_springfilters.controller;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nobodyknows.learning_springfilters.GUIDUtils;

@RestController
@RequestMapping(value = "/user/") 
public class UserController {
	
	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;
	
	@RequestMapping(value = "/sign_in", method = RequestMethod.POST)
	public String signIn (@RequestBody String json) {
		
		String key = "user:access_token:1:svi23k423nsdi";
		String value = GUIDUtils.generateNewGUID();
		redisTemplate.opsForValue().set(key, value);
		
		return "key:" + key + ", value:" + value;
		
	}
	
	@RequestMapping(value = "/get_user_info/{user_id}", method = RequestMethod.GET)
	public String getUserInfo (@PathVariable("user_id") Long userId) {
		
		
		return userId.toString();
	}
	
	@RequestMapping(value = "/invalid_user", method = RequestMethod.GET)
	public String invalidUser () {
		
		return "invalid user";
		
	}

}
