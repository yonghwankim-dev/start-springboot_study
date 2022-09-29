package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Object dest = request.getSession().getAttribute("dest");
        String nextUrl = null;

        if(dest != null){
            request.getSession().removeAttribute("dest");
            nextUrl = (String) dest;
        }else{
            nextUrl = super.determineTargetUrl(request, response);
        }
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
