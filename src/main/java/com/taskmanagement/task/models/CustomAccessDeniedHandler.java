package com.taskmanagement.task.models;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	 private static final Logger LOGGER = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

	    @Override
	    public void handle(HttpServletRequest request,
	                       HttpServletResponse response,
	                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
	        // Erişim reddedildiğinde ne yapılacağını burada tanımla
	        LOGGER.log(Level.WARNING, "Access denied for user: " + request.getUserPrincipal().getName() +
	                " - Path: " + request.getRequestURI());

	        // Kullanıcının geldiği sayfaya geri dön
	        String redirectUrl = determineTargetUrl(request);
	        response.sendRedirect(redirectUrl);
	    }

	    private String determineTargetUrl(HttpServletRequest request) {
	        // Kullanıcının geldiği sayfanın URL'sini al
	        String requestUrl = request.getRequestURI();

	        // Eğer bu sayfa /error ise, ana sayfaya yönlendir
	        if (requestUrl.equals("/error")) {
	            return "/index.html";
	        }

	        // Diğer durumlarda, kullanıcının geldiği sayfaya geri dön
	        return "/accessDenied.html";
	    }
}
