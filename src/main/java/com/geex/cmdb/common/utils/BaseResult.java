package com.geex.cmdb.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/15 19:35
 * @Description: TODO
 */
@Data
public class BaseResult implements Serializable {

    private boolean success = false;
    private String rspCode;
    private String rspMsg;
    private Object data;

    public BaseResult(String rspCode, String rspMsg, Object data) {
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.data = data;
    }

    public BaseResult(boolean success, String rspCode, String rspMsg, Object data) {
        this.success = success;
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.data = data;
    }

    public static BaseResult success(String rspCode, String rspMsg, Object data){
        return new BaseResult(true,rspCode, rspMsg,data);
    }

    public static BaseResult fail(String rspCode, String rspMsg){
        return new BaseResult(rspCode, rspMsg,null);
    }

}
