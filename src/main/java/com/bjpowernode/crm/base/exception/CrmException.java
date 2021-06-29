package com.bjpowernode.crm.base.exception;

public class CrmException extends RuntimeException{
    private CrmEnum crmEnum;
    public CrmException(CrmEnum crmEnum){
        super(crmEnum.getMessage());
        this.crmEnum=crmEnum;
    }

}
