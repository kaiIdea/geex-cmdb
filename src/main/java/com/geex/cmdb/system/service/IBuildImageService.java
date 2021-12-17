package com.geex.cmdb.system.service;

import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.geex.cmdb.system.domain.bo.BuildImageBo;

import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/16 15:02
 * @Description: TODO
 */
public interface IBuildImageService {

    /**
     * 获取项目最后一次构建信息
     * @param nameSpace
     * @param projectName
     * @return
     */
    public String getLastBuildMessage(String nameSpace,String projectName);


    public void createBuild(BuildImageBo buildImageBo);

    public void buildImage(AppBuildMissionBo mission);


    public void queryBuildResult(AppBuiltDetailsBo appBuiltDetailsBo) throws Exception;


    public AjaxResult buildRelease(Map<String,String> map);
}
