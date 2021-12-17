package com.geex.cmdb.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.geex.cmdb.common.utils.DateUtils;
import com.geex.cmdb.system.domain.vo.BranchesVo;
import com.geex.cmdb.system.domain.vo.CommitsVo;
import com.geex.cmdb.system.service.IApplicationManageMessageService;
import com.geex.cmdb.system.service.IGitLabLogService;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Namespace;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 10:05
 * @Description: TODO
 */
@Service
@Slf4j
public class GitLabLogServiceImpl implements IGitLabLogService {

    private static String HOST = "http://192.168.7.197:8083";

    private static String ACCESS_Token = "Lzzwf2UBx__8TPn9PULi";

    private static Integer DATE_OFFSET = -3;

    private static  GitLabApi gitApi = null;

    @Autowired
    private IApplicationManageMessageService applicationManageMessageService;

    static {
        gitApi = new GitLabApi(HOST,ACCESS_Token);
    }

    @Override
    public void getProjectInfo() throws GitLabApiException {
        Project project = gitApi.getProjectApi().getProject("Fund","geex-check");
        log.info("project:"+project.toString());
    }

    @Override
    public List<String> getGitNameSpace() {
        List<String> list = new ArrayList<>();
        try {
            List<Namespace> namespaces = gitApi.getNamespaceApi().getNamespaces();
            list = namespaces.stream().map(Namespace::getName).collect(Collectors.toList());
        } catch (GitLabApiException e) {
            log.info("*查询git分组失败："+e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getNameSpaceAndProject() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> nameSpaces = getGitNameSpace();
        if(null == nameSpaces || nameSpaces.isEmpty()){
            return result;
        }
        nameSpaces.stream().forEach(item ->{
            Map<String, Object> map = new HashMap<>();
                map.put("value",item);
                map.put("label",item);
                map.put("children",applicationManageMessageService.queryByGitGroup(item));
                result.add(map);
        });
        return result;
    }

    @Override
    public List<BranchesVo> getBranches(String namespace, String projectName) {
        List<BranchesVo> list = new ArrayList<>();
        try {
            List<Branch> branches = gitApi.getRepositoryApi().getBranches(namespace+"/"+projectName,1,20);
            branches.stream().forEach(o ->{
                BranchesVo vo = BranchesVo.builder()
                        .branchName(o.getName())
                        .message(o.getCommit().getMessage())
                        .createDate(DateUtils.dateTime(o.getCommit().getAuthoredDate()))
                        .build();
                list.add(vo);
            });
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CommitsVo> getCommits(String branchName, String... path) {
        List<CommitsVo> voList = new ArrayList<>();
        Date since = DateUtils.convertBeforeDate(DATE_OFFSET);
        Date until = DateUtils.convertBeforeDate(0);
        String url="";
        for(String str:path){
            url+=str;
        }
        try {
            List<Commit> list = gitApi.getCommitsApi().getCommits(url,branchName,since,until);
            list.stream().forEach(o->{
                String message = o.getMessage().replace("\n","");
                CommitsVo vo = CommitsVo.builder()
                        .messageCommit(message)
                        .messageAllCommit(o.getAuthorName()+" - "+message)
                        .authorName(o.getAuthorName())
                        .committedDate(DateUtils.dateTime1(o.getCommittedDate()))
                        .build();

                voList.add(vo);
            });
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return voList;
    }
}
