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
 * @Date: 2021/12/6 16:51
 * @Description: TODO
 */
@Builder
@Data
public class HarborImage implements Serializable {

    private String digest;
    private Integer repositoryId;
    private Integer id;
    private Integer projectId;
    private String tagName;
    //推送实践
    private String pushTime;
    //最后拉取实践
    private String pullTime;

    public static List<HarborImage> getHarborImage(String body){
        List<HarborImage> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(body);
        ListIterator<Object> listIterator =  jsonArray.listIterator();
        while (listIterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) listIterator.next();
            JSONArray tags = jsonObject.getJSONArray("tags");
            JSONObject tag = tags.getJSONObject(0);
            HarborImage image = HarborImage.builder()
                    .digest(convertJSON("digest", jsonObject, String.class))
                    .repositoryId(convertJSON("repository_id", jsonObject, Integer.class))
                    .id(convertJSON("id", jsonObject, Integer.class))
                    .projectId(convertJSON("project_id", jsonObject, Integer.class))
                    .pushTime(DateUtils.convertDateStr(convertJSON("push_time", jsonObject, Date.class)))
                    .pullTime(DateUtils.convertDateStr(convertJSON("pull_time", jsonObject, Date.class)))
                    .tagName(convertJSON("name", tag, String.class))
                    .build();
            list.add(image);
        }
        return list;
    }

    public static  <T> T convertJSON(String key,JSONObject jsonObject,Class<T> clazz){
        return false == jsonObject.containsKey(key)?null:jsonObject.getObject(key,clazz);
    }

}



