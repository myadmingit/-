package com.tensquare.user.interceptor;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {
   @Autowired
    JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
      //声明拦截器对象和要拦截的路径
       //拦截的业务
        String authHeader = request.getHeader("Authorization");
         if(authHeader!=null && authHeader.startsWith("Bearer ")){
             String token = authHeader.substring(7);
             try {
                 Claims claims = jwtUtil.parseJWT(token);
                 if (claims != null) {
                     if ("admin".equals(claims.get("roles"))) {
                         request.setAttribute("admin_claims", claims.get("roles"));
                     }
                     if ("user".equals(claims.get("roles"))) {
                         request.setAttribute("user_claims", claims.get("roles"));
                     }
                 }
             }catch (Exception e){
                 throw  new RuntimeException("令牌不正确");
             }
         }

        return true;

    }
}