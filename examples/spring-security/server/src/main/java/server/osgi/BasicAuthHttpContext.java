package server.osgi;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Delegates to the filter to do the authentication and requires that the authentication
 * was succesfull
 */
public class BasicAuthHttpContext implements HttpContext {
    private Filter filter;

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public String getMimeType(String arg0) {
        return null;
    }

    @Override
    public URL getResource(String arg0) {
        return null;
    }

    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            DummyFilterChain dummyFilter = new DummyFilterChain();
            filter.doFilter(request, response, dummyFilter);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                return false;
            }
            return dummyFilter.isCalled();
        } catch (ServletException e) {
            return false;
        }
    }
    
    class DummyFilterChain implements FilterChain {
        boolean called;

        public boolean isCalled() {
            return called;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
            ServletException {
            called=true;
        }
        
    }
}