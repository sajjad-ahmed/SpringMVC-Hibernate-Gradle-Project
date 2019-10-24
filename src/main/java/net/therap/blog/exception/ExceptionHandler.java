package net.therap.blog.exception;

import net.therap.blog.util.URL;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sajjad.ahmed
 * @since 10/23/19.
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        ModelAndView modelAndView = new ModelAndView(URL.ERROR_VIEW);
        modelAndView.addObject("exceptionType", ex.getClass().getSimpleName() );
        modelAndView.addObject("exceptionMessage",  ex.getMessage());
        return modelAndView;
    }
}
