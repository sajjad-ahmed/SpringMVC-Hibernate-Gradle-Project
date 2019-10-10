package net.therap.blog.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sajjad.ahmed
 * @since 9/15/19.
 */
public class NoCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse hsr = (HttpServletResponse) res;
        hsr.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        hsr.setHeader("Pragma", "no-cache");
        hsr.setDateHeader("Expires", 0);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
