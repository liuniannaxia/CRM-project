package com.bjpowernode.crm.base.bean;

import lombok.Data;

import java.util.List;

@Data
public class BarVo<T> {
    //BarVo里面要想有<T>,则BarVo必须指定泛型 T
    private Integer amount;
    private String titles;
    private List<T> t;
}
