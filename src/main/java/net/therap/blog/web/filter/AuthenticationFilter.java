package net.therap.blog.web.filter;

import net.therap.blog.util.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sajjad.ahmed
 * @since 10/5/19.
 */

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession();

        if (uri.contains("auth") || uri.contains("signup") || uri.contains("post/show") || uri.equals("/")) {
            chain.doFilter(req, res);
        } else {
            if (session.getAttribute(Constants.USER_ID_PARAMETER) != null) {
                long userIdParameter = (long) session.getAttribute(Constants.USER_ID_PARAMETER);
                if (userIdParameter == 0) {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/");
                    requestDispatcher.forward(req, res);
                    chain.doFilter(req, res);
                } else
                    chain.doFilter(req, res);
            }
        }
    }

    public void destroy() {
    }
}
