package com.example.demo.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.example.demo.model.event.Event;
import com.example.demo.repos.EventRepository;

@Component
public class HisotrySaverFilter extends OncePerRequestFilter {

	@Autowired
	private EventRepository history;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Event event = new Event();
		event.setEndpoint(request.getRequestURI());
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		filterChain.doFilter(requestWrapper, responseWrapper);
		String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
		event.setResult(responseBody);
		history.save(event);
		responseWrapper.copyBodyToResponse();
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.equals("/history");
	}

}