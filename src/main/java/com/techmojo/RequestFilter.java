package com.techmojo;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.Filter;

@Component
@Order(1)
public class RequestFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(RequestFilter.class);

    public RequestFilter() {
        log.info("RequestFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //  log.info("Request  doFilter()");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        //response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Authorization,X-Requested-With, remember-me");
        response.setHeader("access-control-expose-headers", "Origin, Content-Type, Authorization");
        response.setHeader("X-Powered-By","");
        response.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("Returning OK 200 for OPTIONS :"+request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
