package com.bjpowernode.crm.base.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_dic_value")
@NameStyle(Style.normal)
public class DictionaryValue {
    @Id
    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;

   // private DictionaryType dictionaryType;
}
