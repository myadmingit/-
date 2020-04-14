package com.tensquare.friend.contrller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addfriend(@PathVariable String friendid, @PathVariable String type) {
        //验证用户是否登录，并且拿到当前用户id
        String token = (String) request.getAttribute("user_claims");
        if (token != null && !"".equals(token)) {
            Claims claims = jwtUtil.parseJWT(token);
            String userid = claims.getId();
            //判断添加好友还是添加非好友
            if (type != null) {
                if ("1".equals(type)) {
                   int flag =  friendService.addFirend(userid,friendid);
                   if(flag==0){
                       return new Result(false, StatusCode.ERROR, "不能重复添加");
                   }
                   if(flag==1){
                       return new Result(true, StatusCode.OK, "添加成功");
                   }

                } else if ("2".equals(type)) {
                    friendService.addFirend(userid,friendid);
                    return new Result(true, StatusCode.OK, "添加成功");
                } else {
                    return new Result(false, StatusCode.ERROR, "参数异常");
                }
            } else {
                return new Result(false, StatusCode.ERROR, "参数异常");
            }
        }
        return new Result(false, StatusCode.ERROR, "您尚未登陆");
    }
}


