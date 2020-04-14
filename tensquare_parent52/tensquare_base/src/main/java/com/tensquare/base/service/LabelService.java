package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private   LabelDao  labelDao;

    @Autowired
   private IdWorker idWorker;


    //查询所有
    public List<Label> findAll() {

       return  labelDao.findAll();
    }
    //根据id查询
    public Label findById(String id) {
       return  labelDao.findById(id).get();
    }
    //保存一条数据
    public  void  save(Label label) {
        label.setId( idWorker.nextId()+"");
         labelDao.save(label);
    }
    //更新一条数据
    public void update(Label label) {
       labelDao.save(label);
    }
    //删除一条数据
    public void delete(String labelId) {
         labelDao.deleteById(labelId);
    }

    //根据条件查询
    public List<Label> findSearch(Label label) {

       return  labelDao.findAll(new Specification<Label>() {
           /**
            *
            * @param root 根对象，表示获取对象属性的的根对象，where id  = label.getxxx
            * @param criteriaQuery 封装的都是查询关键字，比如group by order by
            * @param criteriaBuilder 用来封装条件的对象
            * @return 如果直接返回null，表示不需要任何任何条件
            */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                 //new一个集合用来存放所有的条件
                List<Predicate> labelList = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                //模拟sql语句 where id = % 小明 @
                Predicate  predicatename =  criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                labelList.add(predicatename);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate lablestate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    labelList.add(lablestate);
                }
                //new一个数组作为最终的返回的条件
                Predicate[] parr = new Predicate[labelList.size()];
                 //把list转换为数组
                labelList.toArray(parr);

                return criteriaBuilder.and(parr);
            }
        });
    }
     //分页条件查询
    public Page<Label> findSearchPage(Integer page, Integer size, Label label) {
        //封装分页对象
        Pageable pageable = PageRequest.of(page-1,size);

       return labelDao.findAll(new Specification<Label>() {
           @Override
           public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               //new一个集合用来存放所有的条件
               List<Predicate> labelList = new ArrayList<>();
               if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                   //模拟sql语句 where id = % 小明 @
                   Predicate  predicatename =  criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                   labelList.add(predicatename);
               }

               if(label.getState()!=null && !"".equals(label.getState())){
                   Predicate lablestate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                   labelList.add(lablestate);
               }
               //new一个数组作为最终的返回的条件
               Predicate[] parr = new Predicate[labelList.size()];
               //把list转换为数组
               labelList.toArray(parr);

               return criteriaBuilder.and(parr);



           }
       },pageable);
    }
}
