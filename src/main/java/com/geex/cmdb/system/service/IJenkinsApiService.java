package com.geex.cmdb.system.service;


import com.geex.cmdb.common.utils.BaseResult;
import com.offbytwo.jenkins.model.BuildWithDetails;

import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/15 15:44
 * @Description: TODO
 */
public interface IJenkinsApiService {

    /**
     * 调用jenkins build api
     * @param params
     * @return
     */
    public BaseResult buildProject(Map<String,String> params) throws Exception;



    public String buildConsoleOutput(Long buildnumber,String jobName) throws Exception;


    public BuildWithDetails getBuildInfo(Long buildnumber, String jobName) throws Exception;


}
