/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.filter;

import com.baitaplon.pojo.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Liem Nemo
 */
@WebFilter(urlPatterns = {"/templates/admin/*"})
public class UserFilter implements Filter {

    private HttpServletRequest httpRequest;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession();
        User u = (User) session.getAttribute("user");
        if (u != null) {
            if (u.getRoles() != 1) {
                String loginPage = "/templates/admin/login.xhtml";
                RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
                dispatcher.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            String loginPage = "/templates/admin/login.xhtml";
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
