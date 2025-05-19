package com.sbdemo

import grails.converters.JSON
import groovy.util.logging.Slf4j
import org.grails.web.converters.exceptions.ConverterException

import javax.servlet.*
import javax.servlet.http.*
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
@Slf4j
class RequestLoggingFilter implements Filter {


    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request
        if (req.getContentType()?.contains("application/json")) {
            def body = req.inputStream.text  // Read the body once
            def wrapped
            try {
                def bodyJson = JSON.parse(body)
                if (bodyJson.role) {
                    bodyJson.role = "changed " + bodyJson.role
                }
                wrapped= new CachedBodyHttpServletRequest(req, bodyJson.toString())
            }catch(ConverterException exception){
                log.error(exception.message,exception)
                wrapped= new CachedBodyHttpServletRequest(req, body.toString())
            }
            chain.doFilter(wrapped, response)
        } else {
            chain.doFilter(request, response)
        }
    }
}
