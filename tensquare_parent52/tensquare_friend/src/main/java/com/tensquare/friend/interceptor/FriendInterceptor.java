package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FriendInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    //获取令牌信息
        String tokens = request.getHeader("Authorization");
       if(tokens.startsWith("Bearer ")&&!"".equals(tokens)){
           String token  = tokens.substring(7);
           try{
               Claims claims = jwtUtil.parseJWT(token);
               if(claims!=null){
                   String role = claims.get("roles", String.class);
                   if("admin".equals(role)){
                       request.setAttribute("admin_claims", token);
                   }
                   if("user".equals(role)){
                       request.setAttribute("user_claims", token);
                   }
               }
           }catch (Exception e){
               throw new RuntimeException("令牌不正确");
           }
       }
        return true;
    }
}
