package com.project.backend.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// Filter on Servlet. Specification on SecurityConfig while registering Filter
@Component
public final class LoggingFilter implements Filter {

    private FilterConfig filterConfigObj = null;

    public void init(FilterConfig filterConfigObj) {
        this.filterConfigObj = filterConfigObj;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String remoteAddress =  request.getRemoteAddr();
        String uri = ((HttpServletRequest) request).getRequestURI();
        String protocol = request.getProtocol();

        chain.doFilter(request, response);
        filterConfigObj.getServletContext().log("Logging Filter. User Logged in: User IP: " + remoteAddress +" Resource File: " + uri + " Protocol: " + protocol);
    }

    public void destroy() { }
}
