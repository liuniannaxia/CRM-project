package com.bjpowernode.crm.base.bean;

import lombok.Data;


//给用户端返回数据或者消息
@Data
public class ResultVo {
    //看返回是否成功

    private Boolean isOk;

    private String message;
}
