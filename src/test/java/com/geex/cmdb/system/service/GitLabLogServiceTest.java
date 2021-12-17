package com.geex.cmdb.system.service;

import com.alibaba.fastjson.JSON;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 10:12
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class GitLabLogServiceTest {
    @Autowired
    IGitLabLogService gitLabLogService;
    @Autowired
    ISysDictDataService dictDataService;
    @Autowired
    IApplicationManageMessageService applicationManageMessageService;
    @Test
    public void getProjectInfo() throws GitLabApiException {
        gitLabLogService.getProjectInfo();
        System.out.println();
    }

    @Test
    public void selectDictLabel(){
        String a = dictDataService.selectDictLabel("gitlab_group","zijin");
        System.out.println();
    }

    @Test
    public void queryByGitGroup(){
        Object o = applicationManageMessageService.queryByGitGroup("Fund");
        System.out.println();
    }

    @Test
    public void getNameSpaceAndProject(){
        Object o = gitLabLogService.getNameSpaceAndProject();
        String a = JSON.toJSONString(o);
        System.out.println();
    }

    @Test
    public void getBranches(){
        Object o = gitLabLogService.getBranches("Fund","geex-check");
        String a = JSON.toJSONString(o);
        System.out.println();
    }


    @Test
    public void getCommits(){
        Object o = gitLabLogService.getCommits("migratetok8s",new String[]{"Fund","/","geex-check"});
        String a = JSON.toJSONString(o);
        System.out.println();
    }

}
