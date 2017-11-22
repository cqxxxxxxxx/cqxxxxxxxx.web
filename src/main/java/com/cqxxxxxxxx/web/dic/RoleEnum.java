package com.cqxxxxxxxx.web.dic;

/**
 * Created by BG307435 on 2017/11/16.
 */
public enum  RoleEnum {

    USER("普通用户"),
    ADMIN("管理员");

    private String des;

    RoleEnum(String des) {
        this.des = des;
    }
}
