package com.geex.cmdb.system.service.impl;

import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.utils.BaseResult;
import com.geex.cmdb.common.utils.RedisUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.domain.AppBuildMission;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.geex.cmdb.system.domain.bo.BuildImageBo;
import com.geex.cmdb.system.service.IAppBuildMissionService;
import com.geex.cmdb.system.service.IAppBuiltDetailsService;
import com.geex.cmdb.system.service.IBuildImageService;
import com.geex.cmdb.system.service.IJenkinsApiService;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/16 15:10
 * @Description: TODO
 */

@Service
@Slf4j
public class BuildImageServiceImpl implements IBuildImageService {

    private static String LAST_BUILD_IMAGE = "lastBuildMessage:";


    @Autowired
    IAppBuildMissionService iAppBuildMissionService;

    @Autowired
    IJenkinsApiService jenkinsApiService;

    @Autowired
    IAppBuiltDetailsService appBuiltDetailsService;

    @Override
    public String getLastBuildMessage(String nameSpace, String subProjectName) {
        String key = LAST_BUILD_IMAGE+nameSpace+":"+subProjectName;
        return RedisUtils.getCacheObject(key);
    }


    @Override
    public void createBuild(BuildImageBo buildImageBo) {
        setBuildLastMessage(buildImageBo);
        AppBuildMissionBo missionBo = new AppBuildMissionBo();
        String[] str = buildImageBo.getSelectAppValue();
        missionBo.setProjectName(str[1]);
        missionBo.setSubProjectName(buildImageBo.getSubProject());
        missionBo.setBranch(buildImageBo.getAppBranch());
        missionBo.setCommitMessage(buildImageBo.getCommit());
        missionBo.setAppPort(buildImageBo.getPort());
        missionBo.setXxlPort(buildImageBo.getXxlPort());
        missionBo.setRunProfile(buildImageBo.getProfiles());
        missionBo.setGitGroup(str[0]);
        iAppBuildMissionService.insertByBo(missionBo);
    }

    @Override
    public void buildImage(AppBuildMissionBo mission) {
        AppBuiltDetailsBo detailsBo = new AppBuiltDetailsBo();
        detailsBo.setBuildId(mission.getId());
        detailsBo.setBuildName(mission.getSubProjectName());
        detailsBo.setBuildTime(new Date());
        appBuiltDetailsService.insertByBo(detailsBo);
        //缓存项目最近构建的分支信息
        Map<String,String> map = new HashMap<>();
        map.put("jobName",mission.getSubProjectName());
        map.put("branch",mission.getBranch());
        //构建状态，默认初始化
        try {
            BaseResult result = jenkinsApiService.buildProject(map);
            if(result.isSuccess()) {
                Long number = (Long) result.getData();
                detailsBo.setProcessId(number.intValue());
                appBuiltDetailsService.updateByBo(detailsBo);
            }
        } catch (Exception e) {
            log.info("构建失败："+e);
        }

    }
    public void setBuildLastMessage(BuildImageBo buildImageBo){
        String[] str = buildImageBo.getSelectAppValue();
        if(null != str && StringUtils.isNotNull(buildImageBo.getSubProject())){
            String key = LAST_BUILD_IMAGE+str[0]+":"+str[1];
            RedisUtils.setCacheObject(key,buildImageBo.getSubProject());
        }
    }


    @Override
    public void queryBuildResult(AppBuiltDetailsBo appBuiltDetailsBo) throws Exception {
        BuildWithDetails withDetails = jenkinsApiService.getBuildInfo(appBuiltDetailsBo.getProcessId().longValue(),appBuiltDetailsBo.getBuildName());
        BuildResult result = withDetails.getResult();
        //成功
        if(compareBuildResult(result,BuildResult.SUCCESS)){
            appBuiltDetailsBo.setBuildResult(1);
            appBuiltDetailsService.updateByBo(appBuiltDetailsBo);
            return;
        }
        //不成功，便失败
        appBuiltDetailsBo.setBuildResult(2);
        appBuiltDetailsService.updateByBo(appBuiltDetailsBo);
    }

    @Override
    public AjaxResult buildRelease(Map<String, String> map) {
        return null;
    }


    //相等时，返回true
    public boolean compareBuildResult(BuildResult result,BuildResult target){
        return result.compareTo(target) == 0;
    }

    public static void main(String[] args) {
        BuildResult[] rr = BuildResult.values();
        AppBuiltDetailsBo appBuiltDetailsBo = new AppBuiltDetailsBo();
        appBuiltDetailsBo.setBuildResult(BuildResult.FAILURE.ordinal());
        System.out.println();
    }
}
