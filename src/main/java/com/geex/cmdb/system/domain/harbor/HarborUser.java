package com.geex.cmdb.system.domain.harbor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geex.cmdb.common.utils.DateUtils;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 11:29
 * @Description: TODO
 */
@Data
@Builder
public class HarborUser implements Serializable {

    private Integer userId;
    private String username;
    private String email;
    private String realName;
    private String comment;
    private Integer roleId;
    private String roleName;
    //修改时间
    private String updateTime;
    //创建时间
    private String creationTime;

    public static HarborUser getHarborUserMessage(String body) {
        JSONArray jsonArray = JSONArray.parseArray(body);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        HarborUser user = HarborUser.builder()
                .userId(convertJSON("user_id", jsonObject, Integer.class))
                .username(convertJSON("username", jsonObject, String.class))
                .email(convertJSON("name", jsonObject, String.class))
                .realName(convertJSON("realname", jsonObject, String.class))
                .comment(convertJSON("comment", jsonObject, String.class))
                .roleId(convertJSON("role_id", jsonObject, Integer.class))
                .roleName(convertJSON("role_name", jsonObject, String.class))
                .creationTime(DateUtils.convertDateStr(convertJSON("creation_time", jsonObject, Date.class)))
                .updateTime(DateUtils.convertDateStr(convertJSON("update_time", jsonObject, Date.class)))
                .build();
        return user;
    }

    public static  <T> T convertJSON(String key,JSONObject jsonObject,Class<T> clazz){
        return false == jsonObject.containsKey(key)?null:jsonObject.getObject(key,clazz);
    }

}
