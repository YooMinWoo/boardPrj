package com.example.noticePrj.security.handler;

import com.example.noticePrj.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("AccessDeniedHandler")
public class FormAccessDeniedHandler implements AccessDeniedHandler {


	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

		System.err.println("No Authorities" + accessDeniedException);
		System.err.println("Request Uri : " + request.getRequestURI());

		ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "권한이 필요합니다.", null);

		ObjectMapper objectMapper = new ObjectMapper();
		String responseBody = objectMapper.writeValueAsString(apiResponse);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);

	}
}
