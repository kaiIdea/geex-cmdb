package com.geex.cmdb.system.service;

import com.geex.cmdb.system.domain.vo.BranchesVo;
import com.geex.cmdb.system.domain.vo.CommitsVo;
import org.gitlab4j.api.GitLabApiException;

import java.util.List;
import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 10:02
 * @Description: gitlab代码提交版本日志接口
 */
public interface IGitLabLogService {


    void getProjectInfo() throws GitLabApiException;


    List<String> getGitNameSpace();


    List<Map<String,Object>> getNameSpaceAndProject();


    List<BranchesVo> getBranches(String namespace, String projectName);


    List<CommitsVo> getCommits(String branchName, String... path);
}
