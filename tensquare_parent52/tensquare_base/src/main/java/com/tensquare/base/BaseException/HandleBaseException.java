
package com.tensquare.base.BaseException;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常类
 */
@RestControllerAdvice
public class HandleBaseException {
     @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
         e.printStackTrace();
         return new Result(false, StatusCode.ERROR,e.getMessage());
     }

}
