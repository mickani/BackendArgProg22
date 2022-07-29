package com.proyectoFinal.portfolio.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    
    
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("Fail en el método COMMNECE");
        String s= "No Autorizado";
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, s);
        /*res.resetBuffer();
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setHeader("Content-Type", "application/json");
        res.getOutputStream().print("{\"status\":\"401,\"error\":\"Unauthorized\",\"message\":\"No esta autorizado\"}");
        res.flushBuffer();*/
    }
    
}
