package com.example.boardPrj.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "로그인에 실패했습니다.";

        if(exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 틀렸습니다.";
        }
        else if(exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다.";
        }

//        errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
        request.getSession().setAttribute("errorMessage",errorMessage);

        setDefaultFailureUrl("/login");

        super.onAuthenticationFailure(request, response, exception);

    }
}
