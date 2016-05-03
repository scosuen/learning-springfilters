package com.nobodyknows.learning_springfilters.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionFilter implements Filter {
	
	public static final List<String> UNPERMITTED_URI = new ArrayList<String>(){
		{
			add("/user/invalid_user");
			add("/user/sign_in");
		}
	};

	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (UNPERMITTED_URI.contains(httpRequest.getRequestURI())) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}

		if (Optional.ofNullable(redisTemplate.opsForValue().get("user:access_token:" + httpRequest.getHeader("user_id") + ":" + httpRequest.getHeader("device_id")))
				.filter(o -> Optional.ofNullable(httpRequest.getHeader("acc_token")).orElse("null").equals(o)).isPresent()) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			httpResponse.sendRedirect("/user/invalid_user");
			// httpRequest.getRequestDispatcher("/user/invalid_user").forward(httpRequest,httpResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
