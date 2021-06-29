package com.bjpowernode.crm.settings.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
//为了让数据库表名和实体类名字保持一致
@Table(name = "tbl_dic_type")
@Data
@NameStyle(Style.normal)
public class Type {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String code;
    private String name;
    private String description;
}
