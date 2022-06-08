package com.example.demo.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.example.demo.model.event.Event;
import com.example.demo.repos.EventRepository;

@Component
public class HisotryInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private EventRepository history;


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView)
			throws IOException {
		Event event = new Event();
		event.setEndpoint(request.getRequestURI());
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		byte[] responseBody = responseWrapper.getContentInputStream().readAllBytes();
		event.setResult(new String(responseBody, StandardCharsets.UTF_8));
		history.save(event);
	}
}