package com.lgcns.inspire3_blog.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements Filter{

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void doFilter(
            ServletRequest  request, 
            ServletResponse response, 
            FilterChain chain)
    throws IOException, ServletException {
        
        System.out.println("[debug] >>> JwtFilter doFilter");
        HttpServletRequest  req = (HttpServletRequest)request ; 
        HttpServletResponse res = (HttpServletResponse)response ;
        
        String path   = req.getRequestURI() ; 
        System.out.println("[debug] >>> client path "+path); 
        String method = req.getMethod() ;
        System.out.println("[debug] >>> client method : "+method);  

        // OPTIONS 요청은 바로 통과 (CORS는 SecurityConfig에서 처리) 이부분 바꿈 통합 필요
        // if ("OPTIONS".equalsIgnoreCase(method)) {
        //     chain.doFilter(request, response);
        //     return;
        // }
        // preflight 문제 해결
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            res.setHeader("Access-Control-Allow-Credentials", "true");

            chain.doFilter(request, response);
            return ;
        }

        // 인가 정보가 필요 없을 경우
        if(isPath(path)) {
            System.out.println(">>>>> 인증/인가 없이 필터 통과");
            chain.doFilter(request, response) ;
            return ;
        }

        // 인가 정보가 필요한 경우
        String authHeader = req.getHeader("Authorization");
        System.out.println(">>>>> Authorization : "+authHeader); 
        if( authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>>>> if not Authorization "); 
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
            return ;
        }

        String token = authHeader.substring(7); 
        System.out.println(">>>>> token : "+token); 

        try{
            System.out.println(">>>>> token validation "); 
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            
            System.out.println(">>>>> 검증 성공 : 컨트롤로 이동"); 
            chain.doFilter(request, response);
        }catch(Exception e) {
            System.out.println(">>>>> 검증 실패 : 컨트롤러 이동 실패"); 
            e.printStackTrace();
            return ; 
        }

    }

    // 특정 endpoint에 대해서는 인가 없이 컨트롤러 이동이 가능하도록
    public boolean isPath(String path) {
    return path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/api/users/signup")
            || path.startsWith("/api/users/signin")
            || path.startsWith("/api/fortune") // 추후에 연결시 제거해야됌 권환 필요
            || path.startsWith("/api/summary") // 추후에 연결시 제거해야됌 권환 필요
            || path.startsWith("/api/users/logout") // 추후에 연결시 제거해야됌 권환 필요
            || path.startsWith("/api/v1/boards")    // main branch merge 전 제거
            || path.startsWith("/api/v1/comments")    // main branch merge 전 제거
            || path.startsWith("/api/v1/todos")    // main branch merge 전 제거
            || path.startsWith("/api/v1/comments/*/list")
            || path.startsWith("/api/v1/weather/short-term/info");
    }       
}