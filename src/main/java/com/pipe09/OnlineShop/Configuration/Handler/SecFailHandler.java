package com.pipe09.OnlineShop.Configuration.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class SecFailHandler implements AuthenticationFailureHandler {
    /*
    v1
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username=request.getParameter("username");
        log.info("username"+":login fail"+exception.getMessage());
        response.sendRedirect("/login");
    }
     */
    /*
    BadCredentialsException : 비밀번호불일치
    UsernameNotFoundException : 계정없음
    AccountExpiredException : 계정만료
    CredentialsExpiredException : 비밀번호만료
    DisabledException : 계정비활성화
    LockedException : 계정잠김


     */
    /*

    v2

     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username=request.getParameter("username");
        String except = getException( exception );
        log.info( username +":login fail"+ except );
        response.setContentType("text/html; charset=UTF-8");
        String result = "<script>alert('"+ except +"'); history.go( -1 );</script>";
        response.getWriter().write( result );
        response.getWriter().flush();




    }


    public String getException( AuthenticationException exception ){
        if (exception instanceof BadCredentialsException) {
            return "비밀번호 불일치";
        } else if (exception instanceof UsernameNotFoundException) {
            return "계정 없음";
        } else if (exception instanceof AccountExpiredException) {
            return "계정 만료";
        } else if (exception instanceof CredentialsExpiredException) {
            return "비밀번호 만료";
        } else if (exception instanceof DisabledException) {
            return "계정 비 활성화";
        } else if (exception instanceof LockedException) {
            return "계정 잠김";
        } else {
            return "서버내에 장애로 인해 발생하였습니다.";
        }
    }

}
