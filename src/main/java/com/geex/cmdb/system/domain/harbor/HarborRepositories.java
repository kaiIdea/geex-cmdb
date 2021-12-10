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
 * @Date: 2021/12/6 16:37
 * @Description: TODO
 */
@Builder
@Data
public class HarborRepositories implements Serializable {

    private Integer repositoriesId;
    private Integer projectId;
    private Integer artifactCount;
    private Integer pullCount;
    private String name;
    //修改时间
    private String updateTime;
    //创建时间
    private String creationTime;

    public static List<HarborRepositories> getHarborRepositories(String body) {
        List<HarborRepositories> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(body);
        ListIterator<Object> listIterator =  jsonArray.listIterator();
        while (listIterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) listIterator.next();
            HarborRepositories repositories = HarborRepositories.builder()
                    .repositoriesId(convertJSON("id", jsonObject, Integer.class))
                    .projectId(convertJSON("project_id", jsonObject, Integer.class))
                    .artifactCount(convertJSON("artifact_count", jsonObject, Integer.class))
                    .pullCount(convertJSON("pull_count", jsonObject, Integer.class))
                    .name(convertJSON("name", jsonObject, String.class))
                    .creationTime(DateUtils.convertDateStr(convertJSON("creation_time", jsonObject, Date.class)))
                    .updateTime(DateUtils.convertDateStr(convertJSON("update_time", jsonObject, Date.class)))
                    .build();
            list.add(repositories);
        }
        return list;
    }

    public static  <T> T convertJSON(String key,JSONObject jsonObject,Class<T> clazz){
        return false == jsonObject.containsKey(key)?null:jsonObject.getObject(key,clazz);
    }
}
