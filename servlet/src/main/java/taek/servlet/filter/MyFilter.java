package taek.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.UUID;

@Slf4j
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter 생성");
    }

    @Override
    public void destroy() {
        log.info("MyFilter 삭제");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("MyFilter 실행");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        try{
            log.info("request: {} || {}", requestURI, uuid);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e){
            throw e;
        } finally {
            log.info("response: {} || {}", requestURI, uuid);
        }
    }
}
