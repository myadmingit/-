package com.tensquare.base.controller;


import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin   //允许跨域的注解
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    //查询所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
       List<Label> labels = labelService.findAll();
       return new Result(true, StatusCode.OK,"查询成功",labels);

    }
     //根据id查询
   @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public  Result findById(@PathVariable String labelId ){
       Label label = labelService.findById(labelId);
       return new Result(true, StatusCode.OK,"查询成功",label);
  }
    //保存
   @RequestMapping(method = RequestMethod.POST)
    public  Result save(@RequestBody Label label){
       labelService.save(label);
       return new Result(true, StatusCode.OK,"添加成功");
  }
    //更新
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public  Result update(@PathVariable String labelId ,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }
    //根据id删除
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public  Result delete(@PathVariable String labelId){
        labelService.delete(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    //条件查询
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
       List<Label> labelList =  labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",labelList);
    }

    //条件分页查询
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearchPage(@PathVariable Integer page,
                                 @PathVariable Integer size,
                                 @RequestBody Label label){
   Page<Label> labelpage =  labelService.findSearchPage(page,size,label);
            return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(labelpage.getTotalElements(),labelpage.getContent()));
    }
}
