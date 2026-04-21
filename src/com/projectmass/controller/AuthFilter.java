package com.projectmass.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


//@WebFilter("/*") // This tells the "Bouncer" to check EVERY page
public class AuthFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login.jsp";
        String loginServletURI = req.getContextPath() + "/login";

        // Check if the user is logged in
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        // Check if they are just trying to get TO the login page
        boolean loginRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(loginServletURI);

        if (loggedIn || loginRequest) {
            // If they are logged in, or just trying to log in, let them pass
            chain.doFilter(request, response);
        } else {
            // Otherwise, kick them back to the login page!
            res.sendRedirect(loginURI);
        }
    }
}
