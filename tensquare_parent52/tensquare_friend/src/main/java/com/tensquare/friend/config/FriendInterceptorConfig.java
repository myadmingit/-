package com.tensquare.friend.config;

import com.tensquare.friend.interceptor.FriendInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class FriendInterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private FriendInterceptor friendInterceptor;
  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(friendInterceptor)
              .addPathPatterns("/**")
              .excludePathPatterns("/**/login/**");
  }
}
