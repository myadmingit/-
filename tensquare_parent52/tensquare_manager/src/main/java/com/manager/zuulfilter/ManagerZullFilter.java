package com.manager.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerZullFilter extends ZuulFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String header = request.getHeader("Authorization");
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        if (request.getRequestURL().indexOf("login")>0){
            return null;
        }
        if (header != null && !"".equals(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (claims != null) {
                        if (claims.get("roles") != null && "admin".equals(claims.get("roles"))) {
                            currentContext.addZuulRequestHeader("Authorization",header);
                       return null;
                        }
                    }
                } catch (Exception e) {
                    currentContext.setSendZuulResponse(false);//终止运行
                }

            }
        }
        currentContext.setSendZuulResponse(false);//终止运行
        currentContext.setResponseStatusCode(403);//http状态码
        currentContext.setResponseBody("权限不足");//http状态码
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
