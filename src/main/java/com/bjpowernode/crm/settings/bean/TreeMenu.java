package com.bjpowernode.crm.settings.bean;

import lombok.Data;

//{ id:"1", pId:"0", name:"随意勾选 1", open:true}
@Data
public class TreeMenu {

    private String id;
    private String pId;
    private String name;
    private boolean open;
    private boolean checked;

}
