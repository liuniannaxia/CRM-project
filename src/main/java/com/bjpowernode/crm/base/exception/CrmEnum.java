package com.bjpowernode.crm.base.exception;

public enum CrmEnum {
    USER_LOGIN_ACCOUNT_PASSWORD("001-002","用户名或者密码错误"),
    USER_LOGIN_ACCOUNT_EXPIRED("001-N003","账号失效"),
    USER_LOGIN_ACCOUNT_LockState("001-N004","账号被锁定，请联系管理员"),
    USER_LOGIN_ACCOUNT_IP("001-N005","用户IP地址不正确"),
    ACTIVITY_CREAT("002-N001","添加市场活动用户信息失败"),
    ACTIVITY_UPDATE("002-N002","修改市场活动用户信息失败"),
    ACTIVITY_DELETE("002-N003","删除市场活动用户信息失败");

    //code  不同模块的编码
    private String code;
    private String message;//错误信息

    CrmEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
