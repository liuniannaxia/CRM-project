package com.bjpowernode.crm.base.cache;

import com.bjpowernode.crm.base.bean.DictionaryType;
import com.bjpowernode.crm.base.bean.DictionaryValue;
import com.bjpowernode.crm.base.mapper.DictionaryTypeMapper;
import com.bjpowernode.crm.base.mapper.DictionaryValueMapper;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.*;

@Component
public class CrmCache {

    /*方式1，采用Map集合存储
            Map<String,List<String>>
       方式2，List<DicTye>
    * */


    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;
    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserMapper userMapper;


    /*//方式2，List<DicTye>
    @PostConstruct       //JDK自带的注解，作用是在创建对象的时候自动调用其下面的方法
    public void cache(){
        //先查询出所有的类性值
        List<DictionaryType> dictionaryTypes = dictionaryTypeMapper.selectAll();
        //遍历这个类型集合
        for (DictionaryType dictionaryType : dictionaryTypes) {
            String typeCode = dictionaryType.getCode();
            DictionaryValue dictionaryValue = new DictionaryValue();
            dictionaryValue.setTypeCode(typeCode);
            List<DictionaryValue> dictionaryValues = dictionaryValueMapper.select(dictionaryValue);
            //把字典值设置到字典类型中
            dictionaryType.setDictionaryValues(dictionaryValues);

            //把数据字典数据设置到servletContex中
            servletContext.setAttribute("dictionaryTypes",dictionaryTypes);
        }
    }*/


    //方式1     Map<String,List<DictionaryValue>>
    @PostConstruct
    public void cache(){
         List<User> users = userMapper.selectAll();
        List<DictionaryType> dictionaryTypes = dictionaryTypeMapper.selectAll();
        Map<String,List<DictionaryValue>> map = new HashMap<>();
        for (DictionaryType dictionaryType : dictionaryTypes) {
            String typeCode = dictionaryType.getCode();

            //根据字典类性查询出对应的字典值

            //因为阶段要排序，所以进行排序
            Example example = new Example(DictionaryValue.class);
            example.createCriteria().andEqualTo("typeCode",typeCode);
            example.setOrderByClause("orderNo asc");
            List<DictionaryValue> dictionaryValues = dictionaryValueMapper.selectByExample(example);


            //把 k 和 v 放到 map 中（所以想办法获取k和v，然后put就行了）（根据表知道k就是code->typrCode,vale）
            map.put(typeCode,dictionaryValues);
        }

        //读取配置数据（属性文件）包名用 . 分割，属性文件扩展名不用写
        ResourceBundle bundle = ResourceBundle.getBundle("mybatis.Stage2Possibility");
        Enumeration<String> keys = bundle.getKeys();
        //放到map中
        //TreeMap   是有序的
        Map<String,String> Stage2Possibility = new TreeMap<>();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = bundle.getString(key);
            Stage2Possibility.put(key,value);
        }

        servletContext.setAttribute("map",map);
        servletContext.setAttribute("users",users);
        servletContext.setAttribute("Stage2Possibility",Stage2Possibility);
    }
}

