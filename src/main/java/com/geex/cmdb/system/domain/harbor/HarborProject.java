package com.geex.cmdb.system.domain.harbor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geex.cmdb.common.utils.DateUtils;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 11:42
 * @Description: TODO
 */
@Data
@Builder
public class HarborProject implements Serializable {


    //项目名称
    private String name;
    //用户Id
    private String ownerId;
    //用户名
    private String ownerName;
    //项目ID
    private String projectId;
    //项目下存储镜像个数
    private Integer repoCount;

    //当前用户角色ID
    private Integer currentUserRoleId;
    //当前用户角色ID列表
    private List<Integer> currentUserRoleIds;

    //元数据信息
    private Map<String,String> metadata;
    private String cveAllowlist;
    //修改时间
    private String updateTime;
    //创建时间
    private String creationTime;


    public static List<HarborProject> getHarborProject(String body) {
        List<HarborProject> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(body);
        ListIterator<Object> listIterator =  jsonArray.listIterator();
        while (listIterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) listIterator.next();
            HarborProject project = HarborProject.builder()
                    .metadata(convertJSON("metadata", jsonObject, Map.class))
                    .currentUserRoleIds(convertJSON("current_user_role_ids", jsonObject, List.class))
                    .name(convertJSON("name", jsonObject, String.class))
                    .ownerId(convertJSON("owner_id", jsonObject, String.class))
                    .ownerName(convertJSON("owner_name", jsonObject, String.class))
                    .projectId(convertJSON("project_id", jsonObject, String.class))
                    .repoCount(convertJSON("repo_count", jsonObject, Integer.class))
                    .creationTime(DateUtils.convertDateStr(convertJSON("creation_time", jsonObject, Date.class)))
                    .updateTime(DateUtils.convertDateStr(convertJSON("update_time", jsonObject, Date.class)))
                    .build();
            list.add(project);
        }
        return list;
    }


    public static  <T> T convertJSON(String key,JSONObject jsonObject,Class<T> clazz){
        return false == jsonObject.containsKey(key)?null:jsonObject.getObject(key,clazz);
    }
}
