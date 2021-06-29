package com.bjpowernode.crm.base.bean;


import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "tbl_dic_type")
@NameStyle(Style.normal)
public class DictionaryType {
    @Id
    private String code;
    private String name;
    private String description;

    //一个type对应的是多个value
    private List<DictionaryValue> dictionaryValues;

}
